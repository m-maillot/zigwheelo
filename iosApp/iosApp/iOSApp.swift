import SwiftUI
import api

@main
class iOSApp: App {
    
    let dependencies: ZigWheeloDependencies
    let store: OnboardUserViewModel
    
    required init() {
        dependencies = ZigWheeloDependencies.Companion().create(baseUrl: "http://192.168.1.11:9580", withLog: true)
        store = OnboardUserViewModel(store: OnboardUser(dependencies: dependencies))
        
        print("API Initialied")
    }
    
    var body: some Scene {
        WindowGroup {
            OnboardingContentView().environmentObject(store)
        }
    }
}

