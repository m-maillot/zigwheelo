package fr.racomach.zigwheelo.parks.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.LatLng
import fr.racomach.api.model.Park
import fr.racomach.api.usecase.SearchParkState
import fr.racomach.zigwheelo.parks.model.ParkModel
import fr.racomach.zigwheelo.parks.model.PositionModel

@Composable
fun MainScreen(state: SearchParkState) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (state) {
            is SearchParkState.Error -> {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    text = "Erreur durant le chargement...",
                    color = Color.Red,
                )
            }
            is SearchParkState.Loaded -> {

            }
            SearchParkState.Loading -> {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    text = "Chargement...",
                    color = Color.Blue,
                )
            }
        }
        GoogleMapView(
            myLocation = LatLng(45.742989978188945, 4.851021720981201),
            parks = if (state is SearchParkState.Loaded) state.parks.map { it.toModel() } else emptyList(),
            onClick = {}
        )
    }
}

private fun Park.toModel() = ParkModel(
    id = id,
    address = address,
    location = PositionModel(latitude = location.latitude, longitude = location.longitude)
)