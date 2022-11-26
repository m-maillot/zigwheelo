//
//  OnboardingScreen.swift
//  iosApp
//
//  Created by Martial Maillot on 25/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct OnboardingScreen<Content>: View where Content : View {
    
    var currentStep: Int
    var content: () -> Content
    
    @inlinable public init(currentStep: Int, @ViewBuilder content: @escaping () -> Content) {
        self.currentStep = currentStep
        self.content = content
    }

    var body: some View {
        ZStack(alignment: .topLeading) {
            Color.zwBasic
                .edgesIgnoringSafeArea(.all)
            ZStack(alignment: .center) {
                ZWColors.background.shadow(color: .black.opacity(0.2), radius: 2, x: 5, y: 5)
                VStack(alignment: .center) {
                    content()
                    Spacer()
                    PageDot(count: 3, active: currentStep)
                }.padding()
            }.padding()
        }
    }
}

struct OnboardingScreen_Previews: PreviewProvider {
    static var previews: some View {
        OnboardingScreen(currentStep: 2) {
            Text("Hello world !")
        }
    }
}
