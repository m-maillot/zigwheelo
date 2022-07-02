import SwiftUI
import api

struct ContentView: View {
    @EnvironmentObject var store: ObservableSearchParks
    @SwiftUI.State var errorMessage: String?
    
	var body: some View {
		ParkList()
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
