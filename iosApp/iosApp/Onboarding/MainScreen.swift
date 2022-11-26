import SwiftUI
import api

struct MainScreen: OnboardUserConnectedView {
    struct Props {
        let state: OnboardingState
        let submitUsername: (String) -> ()
        let skipTrip: () -> Void
    }
    
    func map(state: OnboardingState, dispatch: @escaping OnboardUserDispatchFunction) -> Props {
        return Props(state: state,
                     submitUsername: { username in dispatch(OnboardingAction.CreateUser(username: username)) },
                     skipTrip: { dispatch(OnboardingAction.SkipTrip()) }
        )
    }
    
    func body(props: Props) -> some View {
        ZStack {
            if (props.state.currentStep() == Step.welcome) {
                OnboardingStepOne(
                    onSubmit: props.submitUsername,
                    error: props.state.welcomeStep?.error?.message,
                    loading: props.state.welcomeStep?.loading ?? false,
                    succeed: props.state.welcomeStep?.id != nil
                )
            } else if (props.state.currentStep() == Step.trip) {
                OnboardingStepTwo(skip: props.skipTrip, addTrip: {})
            } else if (props.state.currentStep() == Step.settings) {
                OnboardingStepThree()
            } else {
                Text("Loading")
            }
        }
    }
}
