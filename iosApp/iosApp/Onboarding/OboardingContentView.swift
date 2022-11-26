import SwiftUI
import api

struct OnboardingContentView: View {
    @EnvironmentObject var store: OnboardUserViewModel
    @SwiftUI.State var errorMessage: String?
    
    var body: some View {
        MainScreen()
    }
}
