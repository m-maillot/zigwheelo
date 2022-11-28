import SwiftUI
import api

struct MainScreen: OnboardUserConnectedView {
    struct Props {
        let state: OnboardingState
        let submitUsername: (String) -> ()
        let denyNotification: () -> ()
        let acceptNotification: (Date) -> ()
        let skipTrip: () -> Void
    }
    
    func map(state: OnboardingState, dispatch: @escaping OnboardUserDispatchFunction) -> Props {
        return Props(state: state,
                     submitUsername: { username in dispatch(OnboardingAction.CreateUser(username: username)) },
                     denyNotification: { dispatch(OnboardingAction.UpdateSettings(acceptNotification: false, token: nil, notificationAt: nil))},
                     acceptNotification: { notifyAt in dispatch(OnboardingAction.UpdateSettings(acceptNotification: true, token: "", notificationAt: notifyAt.toLocalTime())) },
                     skipTrip: { dispatch(OnboardingAction.SkipTrip()) }
        )
    }
    
    func body(props: Props) -> some View {
        ZStack {
            if (props.state.currentStep() == Step.welcome) {
                OnboardingProfileStep(
                    onSubmit: props.submitUsername,
                    error: props.state.welcomeStep?.error?.message,
                    loading: props.state.welcomeStep?.loading ?? false,
                    usernameSucceed: props.state.welcomeStep?.username
                )
            } else if (props.state.currentStep() == Step.trip) {
                OnboardingStepTwo(skip: props.skipTrip, addTrip: {})
            } else if (props.state.currentStep() == Step.settings) {
                OnboardingSettingsStep(
                    onDenyNotification: { props.denyNotification() },
                    onAcceptNotification: { notifyAt in props.acceptNotification(notifyAt) },
                    loading: props.state.settingStep?.loading ?? false
                )
            } else {
                Text("Loading")
            }
        }
    }
}
