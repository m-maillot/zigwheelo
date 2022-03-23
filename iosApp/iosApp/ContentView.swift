import SwiftUI
import api

struct ContentView: View {
    let places = ZigWheeloApi.Companion().create(baseUrl: "");
    
    func onClick() {
        places.searchParks(latitude: 42.2, longitude: 4.3, distance: 300) { result, error in
            // TODO Code
        }
    }

	var body: some View {
		Text("greet")
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
