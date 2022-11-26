import Foundation
import CorePermissionsSwiftUI
import PermissionsSwiftUINotification
import SwiftUI

extension OnboardingStepThree {
    @MainActor
    class OnboardingSettingsViewModel : ObservableObject {
        @Published private(set) var needPermission: Bool = true
        
        func getPermissionState() async -> Bool {
            let current = UNUserNotificationCenter.current()
            let result = await current.notificationSettings()
            switch result.authorizationStatus {
            case .notDetermined:
                return false
            case .denied:
                return false
            case .authorized:
                return true
            case .provisional:
                return false
            case .ephemeral:
                return false
            @unknown default:
                return false
            }
        }
        
        @MainActor
        func hasPermission() async {
            needPermission = await getPermissionState()
        }
    }
}
