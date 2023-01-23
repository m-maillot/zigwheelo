//
//  OnboardingStepThree.swift
//  iosApp
//
//  Created by Martial Maillot on 25/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import CorePermissionsSwiftUI
import PermissionsSwiftUINotification

struct OnboardingSettingsStep: View {
    static func defaultDate() -> Date {
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy/MM/dd HH:mm"
        return formatter.date(from: "2022/01/01 07:30") ?? Date()
    }
    
    @State var showModal = false
    @State var currentNotificationAt: Date = OnboardingSettingsStep.defaultDate()
    @StateObject private var viewModel = OnboardingSettingsViewModel()
    
    var onDenyNotification: () -> Void
    var onAcceptNotification: (Date) -> Void
    var loading: Bool

    var body: some View {
        OnboardingScreen(currentStep: 3) {
            Text("Notification").zwTypo(.h1)
            Spacer()
            LottieAnimation(animation: .notification).frame(width: 200, height: 200)
            Spacer()
            Text("Soyez notifié d'un changement de météo pour vos trajets afin de mieux préparer votre journée")
            switch (viewModel.permissionState) {
            case .Accepted:
                Text("Recevoir la notification vers :")
                DatePicker("", selection: $currentNotificationAt, displayedComponents: .hourAndMinute).labelsHidden().environment(\.locale, Locale(identifier: "fr_FR"))
                    .datePickerStyle(.wheel)
                ZWButton(text: "Valider", action: { onAcceptNotification(currentNotificationAt) }).disabled(loading)
            case .Rational:
                ZWButton(text: "Autoriser les notifications", action: { showModal = true})
                    .JMModal(showModal: $showModal, for: [.notification], onDisappear: {
                        Task { await viewModel.checkPermission() }
                    })
            case.Denied:
                Text("Vous ne souhaitez pas recevoir de notification")
                ZWButton(text: "Continuer", action: { onDenyNotification() }).disabled(loading)
            }
        }.task {
            await viewModel.checkPermission()
        }
    }
}

struct OnboardingSettingsStep_Previews: PreviewProvider {
    static var previews: some View {
        OnboardingSettingsStep(
            onDenyNotification: {},
            onAcceptNotification: { _ in },
            loading: false
        )
    }
}
