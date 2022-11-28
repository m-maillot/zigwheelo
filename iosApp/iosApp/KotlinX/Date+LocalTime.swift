//
//  Date+LocalTime.swift
//  iosApp
//
//  Created by Martial Maillot on 28/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import api

extension Date {
    func toLocalTime() -> Kotlinx_datetimeLocalTime {
        let calendar = Calendar.current
        return Kotlinx_datetimeLocalTime(
            hour: Int32(calendar.component(.hour, from: self)),
            minute: Int32(calendar.component(.minute, from: self)),
            second: Int32(calendar.component(.second, from: self)),
            nanosecond: Int32(calendar.component(.nanosecond, from: self))
        )
    }
}
