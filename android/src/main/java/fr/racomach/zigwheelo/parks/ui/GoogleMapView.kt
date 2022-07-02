package fr.racomach.zigwheelo.parks.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*
import fr.racomach.zigwheelo.R
import fr.racomach.zigwheelo.common.hasLocationPermissions
import fr.racomach.zigwheelo.parks.model.ParkModel
import fr.racomach.zigwheelo.utils.getBitmapDescriptor

@Composable
fun GoogleMapView(
    myLocation: LatLng,
    parks: List<ParkModel>,
    onClick: (ParkModel) -> Unit
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(myLocation, 17f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            isBuildingEnabled = false,
            isIndoorEnabled = false,
            isTrafficEnabled = false,
            mapType = MapType.NORMAL,
            mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
                LocalContext.current,
                R.raw.gmap_style
            ),
            isMyLocationEnabled = LocalContext.current.hasLocationPermissions(),
        ),
        uiSettings = MapUiSettings(),
    ) {
        parks.map { park ->
            Marker(
                icon = getBitmapDescriptor(
                    context = LocalContext.current,
                    id = R.drawable.ic_bike_parking_24
                ),
                position = LatLng(park.location.latitude, park.location.longitude),
                title = park.address,
                onClick = { onClick(park).let { false } }
            )
        }
    }
}