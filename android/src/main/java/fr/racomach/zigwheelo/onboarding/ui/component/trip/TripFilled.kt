package fr.racomach.zigwheelo.onboarding.ui.component.trip

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.*
import fr.racomach.api.error.ErrorResponse
import fr.racomach.zigwheelo.R
import fr.racomach.zigwheelo.onboarding.ui.component.PagerDot
import fr.racomach.zigwheelo.ui.theme.ZigWheeloTypography
import fr.racomach.zigwheelo.utils.getBitmapDescriptor

@Composable
fun TripFilled(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    error: ErrorResponse?,
    trip: Trip,
    onSubmit: (trip: Trip) -> Unit,
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = "Trajet", style = ZigWheeloTypography.displayMedium,
        )
        TripPicture(Modifier.size(180.dp), isLoading)
        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            cameraPositionState = CameraPositionState(
                position = CameraPosition.fromLatLngZoom(
                    LatLngBounds.builder().include(trip.start).include(trip.finish).build().center,
                    12f
                )
            ),
            properties = MapProperties(
                isBuildingEnabled = false,
                isIndoorEnabled = false,
                isTrafficEnabled = false,
                mapType = MapType.NORMAL,
                isMyLocationEnabled = false,
            ),
            uiSettings = MapUiSettings(),
        ) {
            Marker(
                icon = getBitmapDescriptor(
                    context = LocalContext.current,
                    id = R.drawable.map_pin_start,
                ),
                position = trip.start,
                title = "Point de départ",
                snippet = "Départ à ${trip.startAt}",
            )
            Marker(
                icon = getBitmapDescriptor(
                    context = LocalContext.current,
                    id = R.drawable.map_pin_finish,
                ),
                position = trip.finish,
                title = "Point d'arrivée",
                snippet = "Durée du trajet : ${trip.durationInMinutes} minutes",
            )
            Polyline(points = listOf(trip.start, trip.finish), color = Color.Red)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedButton(
                onClick = { onSubmit(trip) },
                enabled = !isLoading,
            ) {
                Text(text = "Valider")
            }
        }
        PagerDot(
            count = 3,
            active = 2,
            activeColor = MaterialTheme.colorScheme.primary,
            inactiveColor = MaterialTheme.colorScheme.secondary,
            dotSize = 16.dp
        )
    }
}

@Composable
private fun TripPicture(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(if (isSystemInDarkTheme()) R.raw.trip_dark else R.raw.trip_light)
    )
    LottieAnimation(
        modifier = modifier,
        composition = composition,
        iterations = if (isLoading) LottieConstants.IterateForever else 1,
    )
}