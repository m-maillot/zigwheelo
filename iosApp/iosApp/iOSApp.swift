import SwiftUI
import api

@main
class iOSApp: App {
    
    let api: ZigWheeloApi
    let store: ObservableSearchParks
    
    required init() {
        api = ZigWheeloApi.Companion().create(baseUrl: "http://localhost:8080", withLog: true)
        store = ObservableSearchParks(store: SearchParks(zigWheeloApi: api))
        print("API Initialied")
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView().environmentObject(store)
        }
    }
}

class ObservableSearchParks: ObservableObject {
    @Published public var state: SearchParkState = SearchParkState.Loading()
    @Published public var sideEffect: SearchParkEffect?
    
    let store: SearchParks
    
    var stateWatcher : Closeable?
    var sideEffectWatcher : Closeable?

    init(store: SearchParks) {
        print("Observable init")
        self.store = store
        stateWatcher = self.store.watchState().watch { [weak self] state in
            self?.state = state
        }
        sideEffectWatcher = self.store.watchSideEffect().watch { [weak self] state in
            self?.sideEffect = state
        }
    }
    
    public func dispatch(_ action: SearchParkAction) {
        print("Dispatch action \(action)")
        store.dispatch(action: action)
    }
    
    deinit {
        print("Observable deinit")
        stateWatcher?.close()
        sideEffectWatcher?.close()
    }
}

public typealias DispatchFunction = (SearchParkAction) -> ()

public protocol ConnectedView: View {
    associatedtype Props
    associatedtype V: View
    
    func map(state: SearchParkState, dispatch: @escaping DispatchFunction) -> Props
    func body(props: Props) -> V
}

public extension ConnectedView {
    func render(state: SearchParkState, dispatch: @escaping DispatchFunction) -> V {
        let props = map(state: state, dispatch: dispatch)
        return body(props: props)
    }
    
    var body: StoreConnector<V> {
        return StoreConnector(content: render)
    }
}

public struct StoreConnector<V: View>: View {
    @EnvironmentObject var store: ObservableSearchParks
    let content: (SearchParkState, @escaping DispatchFunction) -> V
    
    public var body: V {
        return content(store.state, store.dispatch)
    }
}
