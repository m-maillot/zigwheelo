import SwiftUI

struct OnboardingStepOne: View {
    
    @State private var username: String = ""
    
    var onSubmit : (_ username: String) -> ()
    var error: String? = nil
    var loading: Bool = false
    var usernameSucceed: String? = nil
    
    var body: some View {
        OnboardingScreen(currentStep: 1) {
            Text("Votre profil").zwTypo(.h1)
            Spacer()
            if (error != nil) {
                LottieAnimation(animation: .sad).frame(width: 150, height: 150)
            } else if (usernameSucceed != nil) {
                LottieAnimation(animation: .wink).frame(width: 150, height: 150)
            } else {
                LottieAnimation(animation: .happy).frame(width: 150, height: 150)
            }
            Spacer()
            if let name = usernameSucceed {
                Text("Bienvenue \(name) !").zwTypo(.h1)
            } else {
                Text("Choississez d'abord un pseudo").zwTypo(.p1)
                ZWTextField(label: "Velotafeur", text: $username, error: error)
                ZWButton(text: "Valider", style: .fill, action: {
                    self.onSubmit(username)
                })
                .disabled(loading)
            }
        }
    }
}

struct OnboardProfil_Previews: PreviewProvider {
    static var previews: some View {
        OnboardingStepOne(onSubmit: { username in
            // Do nothing
        })
    }
}

struct OnboardProfilSucceed_Previews: PreviewProvider {
    static var previews: some View {
        OnboardingStepOne(onSubmit: { username in
            // Do nothing
        }, usernameSucceed: "Pseudo")
    }
}

struct OnboardProfilFailed_Previews: PreviewProvider {
    static var previews: some View {
        OnboardingStepOne(onSubmit: { username in
            // Do nothing
        }, error: "Nom invalide")
    }
}

struct OnboardProfilLoading_Previews: PreviewProvider {
    static var previews: some View {
        OnboardingStepOne(onSubmit: { username in
            // Do nothing
        }, loading: true)
    }
}
