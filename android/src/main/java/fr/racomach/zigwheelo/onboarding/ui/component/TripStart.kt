package fr.racomach.zigwheelo.onboarding.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*
import fr.racomach.zigwheelo.R
import fr.racomach.zigwheelo.common.hasLocationPermissions
import fr.racomach.zigwheelo.ui.theme.ZigWheeloTypography

@Composable
fun TripStart(modifier: Modifier = Modifier) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(45.742989978188945, 4.851021720981201), 17f)
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = "Point de départ", style = ZigWheeloTypography.displayMedium,
        )
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
        )
    }
}

@Preview(
    showBackground = true,
    device = Devices.NEXUS_6,
)
@Composable
private fun TripStartPreview() {
    TripStart()
}