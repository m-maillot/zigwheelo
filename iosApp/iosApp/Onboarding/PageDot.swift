//
//  PageDot.swift
//  iosApp
//
//  Created by Martial Maillot on 21/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct PageDot: View {
    var count: Int
    var active: Int
    var body: some View {
        HStack {
            Spacer()
            ForEach(0..<count, id: \.self) { index in
                Circle()
                    .fill(index < active ? .primary : .secondary)
                    .frame(width: 16, height: 16)
            }
            Spacer()
        }
    }
}

struct PageDot_Previews: PreviewProvider {
    static var previews: some View {
        PageDot(count: 3, active: 1)
    }
}
