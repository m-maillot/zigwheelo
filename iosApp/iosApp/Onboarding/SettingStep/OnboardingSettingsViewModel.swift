import Foundation
import CorePermissionsSwiftUI
import PermissionsSwiftUINotification
import SwiftUI

enum PermissionState {
    case Accepted
    case Rational
    case Denied
}

extension OnboardingSettingsStep {
    @MainActor
    class OnboardingSettingsViewModel : ObservableObject {
        @Published private(set) var permissionState: PermissionState = .Rational
        
        func getPermissionState() async -> PermissionState {
            let current = UNUserNotificationCenter.current()
            let result = await current.notificationSettings()
            switch result.authorizationStatus {
            case .notDetermined:
                return .Rational
            case .denied:
                return .Denied
            case .authorized:
                return .Accepted
            case .provisional:
                return .Accepted
            case .ephemeral:
                return .Accepted
            @unknown default:
                return .Rational
            }
        }
        
        @MainActor
        func checkPermission() async {
            permissionState = await getPermissionState()
        }
    }
}
