//
//  ParkList.swift
//  iosApp
//
//  Created by Martial Maillot on 24/03/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import MapKit
import api

struct ParkList: ConnectedView {
    
    struct Props {
        let error: String?
        let loading: Bool
        let items: [Park]
        let searchParks: () -> Void
    }
    
    func map(state: SearchParkState, dispatch: @escaping DispatchFunction) -> Props {
        print("Map \(state)")
        return Props(error: (state as? SearchParkState.Error)?.detail,
                     loading: state is SearchParkState.Loading,
                     items: (state as? SearchParkState.Loaded)?.parks ?? []) {
            dispatch(SearchParkAction.Search(latitude: 45.742989978188945, longitude: 4.851021720981201, distance: 500))
        }
    }
    
    func body(props: Props) -> some View {
        MainView(parks: props.items.map { $0.toModel() })
            .onAppear {
                props.searchParks()
            }
        /*
         List {
            ForEach(props.items, id: \.id) { ParkRow(park: $0) }
        }.onAppear {
            props.searchParks()
        }
         */
    }
}
