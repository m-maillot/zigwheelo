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

struct OnboardingStepThree: View {
    @State var showModal = false
    @StateObject private var viewModel = OnboardingSettingsViewModel()
        
    var body: some View {
        OnboardingScreen(currentStep: 3) {
            Text("Notification").zwTypo(.h1)
            Spacer()
            LottieAnimation(animation: .notification).frame(width: 200, height: 200)
            Spacer()
            Text("Soyez notifié d'un changement de météo pour vos trajets afin de mieux préparer votre journée")
            if (viewModel.needPermission) {
                Text("OK")
            } else {
                ZWButton(text: "Autoriser les notifications", action: { showModal = true})
                    .JMModal(showModal: $showModal, for: [.notification], onDisappear: {
                        Task { await viewModel.hasPermission() }
                    })
            }
        }.task {
            await viewModel.hasPermission()
        }
    }
}

struct OnboardingStepThree_Previews: PreviewProvider {
    static var previews: some View {
        OnboardingStepThree()
    }
}
