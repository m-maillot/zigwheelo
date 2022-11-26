import SwiftUI
import Foundation
import api

class OnboardUserViewModel: ObservableObject {
    @Published public var state: OnboardingState = OnboardingState(welcomeStep: WelcomeStepState(loading: false, error: nil, id: nil), tripStep: nil, settingStep: nil)
    @Published public var sideEffect: OnboardingEffect?
    
    let store: OnboardUser
    
    var stateWatcher : Closeable?
    var sideEffectWatcher : Closeable?

    init(store: OnboardUser) {
        print("Observable init")
        self.store = store
        stateWatcher = self.store.watchState().watch { [weak self] state in
            self?.state = state
        }
        sideEffectWatcher = self.store.watchSideEffect().watch { [weak self] state in
            self?.sideEffect = state
        }
    }
    
    public func dispatch(_ action: OnboardingAction) {
        print("Dispatch action \(action)")
        store.dispatch(action: action)
    }
    
    deinit {
        print("Observable deinit")
        stateWatcher?.close()
        sideEffectWatcher?.close()
    }
}

public typealias OnboardUserDispatchFunction = (OnboardingAction) -> ()

public protocol OnboardUserConnectedView: View {
    associatedtype Props
    associatedtype V: View
    
    func map(state: OnboardingState, dispatch: @escaping OnboardUserDispatchFunction) -> Props
    func body(props: Props) -> V
}

public extension OnboardUserConnectedView {
    func render(state: OnboardingState, dispatch: @escaping OnboardUserDispatchFunction) -> V {
        let props = map(state: state, dispatch: dispatch)
        return body(props: props)
    }
    
    var body: OnboardUserStoreConnector<V> {
        return OnboardUserStoreConnector(content: render)
    }
}

public struct OnboardUserStoreConnector<V: View>: View {
    @EnvironmentObject var store: OnboardUserViewModel
    let content: (OnboardingState, @escaping OnboardUserDispatchFunction) -> V
    
    public var body: V {
        return content(store.state, store.dispatch)
    }
}
