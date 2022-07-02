//
//  ParkRow.swift
//  iosApp
//
//  Created by Martial Maillot on 24/03/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import api

struct ParkRow: View {
    let park: Park
    
    var body: some View {
        Text("\(park.address)")
    }
}

struct ParkRow_Previews: PreviewProvider {
    static var previews: some View {
        ParkRow(park: Park(id: "1", address: "6 rue lamothe", location: Position(latitude: 4.3, longitude: 42.2)))
    }
}
