//
//  OnboardingStepTwo.swift
//  iosApp
//
//  Created by Martial Maillot on 25/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct OnboardingStepTwo: View {
    var skip: () -> Void
    var addTrip: () -> Void
    
    var body: some View {
        OnboardingScreen(currentStep: 2) {
            Text("Trajet").zwTypo(.h1)
            Spacer()
            LottieAnimation(animation: .trip).frame(width: 200, height: 200)
            Spacer()
            Text("Vous pouvez définir un trajet régulier à vélo. Ainsi vous recevrez chaque jour un notification pour vous aider à bien vous équipez pour la journée !")
            VStack {
                ZWButton(text: "Définir un trajet", action: addTrip)
                ZWButton(text: "Passer", style: .outline, action: skip)
            }
        }
    }
}

struct OnboardingStepTwo_Previews: PreviewProvider {
    static var previews: some View {
        OnboardingStepTwo(skip: {}, addTrip: {})
    }
}
