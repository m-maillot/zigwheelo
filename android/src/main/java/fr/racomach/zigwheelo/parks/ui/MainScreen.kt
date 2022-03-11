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
import fr.racomach.zigwheelo.MainViewModel

@Composable
fun MainScreen(state: MainViewModel.State) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (state) {
            is MainViewModel.State.Error -> {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    text = "Erreur durant le chargement...",
                    color = Color.Red,
                )
            }
            is MainViewModel.State.Loaded -> {

            }
            MainViewModel.State.Loading -> {
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
            parks = if (state is MainViewModel.State.Loaded) state.parks else emptyList(),
            onClick = {}
        )
    }
}