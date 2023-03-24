package fr.racomach.zigwheelo.onboarding.ui.component.trip

import com.google.android.gms.maps.model.LatLng
import kotlinx.datetime.LocalTime

data class Trip(
    val start: LatLng,
    val finish: LatLng,
    val startAt: LocalTime,
    val durationInMinutes: Int,
    val roundTripAt: LocalTime,
)