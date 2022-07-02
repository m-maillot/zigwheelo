//
//  MainView.swift
//  iosApp
//
//  Created by Martial Maillot on 02/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import MapKit

struct MainView: View {
    
    var parks: [ParkModel]
    
    @State private var region = MKCoordinateRegion(
        center: CLLocationCoordinate2D(latitude: 45.742989978188945, longitude: 4.851021720981201),
        span: MKCoordinateSpan(latitudeDelta: 0.01, longitudeDelta: 0.01)
    )

    var body: some View {
        Map(coordinateRegion: $region,
            interactionModes: [.all],
            showsUserLocation: true,
            userTrackingMode: .constant(.follow),
            annotationItems: parks) {
            MapMarker(coordinate: $0.position)
        }.edgesIgnoringSafeArea(.all)
    }
}
