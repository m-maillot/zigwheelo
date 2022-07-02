//
//  Model.swift
//  iosApp
//
//  Created by Martial Maillot on 02/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import MapKit
import api

struct ParkModel: Identifiable {
    let id: String
    let name: String
    let position: CLLocationCoordinate2D
}

extension Park {
    func toModel() -> ParkModel {
        return ParkModel(id: self.id, name: self.address, position: CLLocationCoordinate2D(latitude: self.location.latitude, longitude: self.location.longitude))
    }
}
