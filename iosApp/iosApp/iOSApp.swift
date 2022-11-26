import SwiftUI
import api

@main
class iOSApp: App {
    
    let api: ZigWheeloApi
    let store: OnboardUserViewModel
    
    required init() {
        api = ZigWheeloApi.Companion().create(baseUrl: "http://192.168.1.11:9580", withLog: true)
        store = OnboardUserViewModel(store: OnboardUser(zigWheeloApi: api))
        print("API Initialied")
    }
    
    var body: some Scene {
        WindowGroup {
            OnboardingContentView().environmentObject(store)
        }
    }
}

