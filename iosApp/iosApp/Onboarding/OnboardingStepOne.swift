import SwiftUI

struct OnboardingStepOne: View {
    
    @State private var username: String = ""
    
    var onSubmit : (_ username: String) -> ()
    var error: String? = nil
    var loading: Bool = false
    var succeed: Bool = false
    
    var body: some View {
        OnboardingScreen(currentStep: 1) {
            Text("Bienvenue !").zwTypo(.h1)
            Spacer()
            if (error != nil) {
                LottieAnimation(animation: .sad).frame(width: 150, height: 150)
            } else if (succeed) {
                LottieAnimation(animation: .wink).frame(width: 150, height: 150)
            } else {
                LottieAnimation(animation: .happy).frame(width: 150, height: 150)
            }
            Spacer()
            Text("Choississez d'abord un pseudo").zwTypo(.p1)
            ZWTextField(label: "Velotafeur", text: $username, error: error)
            ZWButton(text: "Valider", style: .fill, action: {
                self.onSubmit(username)
            })
            .disabled(loading)
        }
    }
}

struct OnboardingStepOne_Previews: PreviewProvider {
    static var previews: some View {
        OnboardingStepOne(onSubmit: { username in
            // Do nothing
        })
    }
}
