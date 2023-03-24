package fr.racomach.zigwheelo.common.component

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import fr.racomach.zigwheelo.ui.theme.ZigWheeloTypography
import fr.racomach.zigwheelo.utils.getBitmapDescriptor

data class MarkerOptions(
    @DrawableRes val icon: Int? = null,
    val snippet: String? = null,
    val title: String? = null,
)

data class FixedMarker(
    val position: LatLng,
    val options: MarkerOptions,
)

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SelectPosition(
    modifier: Modifier = Modifier,
    title: String,
    defaultPosition: LatLng,
    markerOptions: MarkerOptions = MarkerOptions(),
    otherMarkers: List<FixedMarker> = emptyList(),
    validationText: String,
    onSubmit: (position: LatLng) -> Unit,
) {
    val notificationPermissionState = rememberMultiplePermissionsState(locationPermission)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultPosition, 17f)
    }


    val startPoint = remember { mutableStateOf<LatLng?>(null) }

    SideEffect {
        notificationPermissionState.launchMultiplePermissionRequest()
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = title, style = ZigWheeloTypography.displayMedium,
        )
        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                properties = MapProperties(
                    isBuildingEnabled = false,
                    isIndoorEnabled = false,
                    isTrafficEnabled = false,
                    mapType = MapType.NORMAL,
                    isMyLocationEnabled = notificationPermissionState.permissions.any { it.status is PermissionStatus.Granted },
                ),
                uiSettings = MapUiSettings(),
                onMapClick = {
                    startPoint.value = it
                },
                cameraPositionState = cameraPositionState,
            ) {
                startPoint.value?.let { position ->
                    Marker(
                        icon = markerOptions.icon?.let {
                            getBitmapDescriptor(
                                context = LocalContext.current,
                                id = it
                            )
                        },
                        position = position,
                        title = markerOptions.title,
                        snippet = markerOptions.snippet,
                    )
                }
                otherMarkers.map { fixedMarker ->
                    Marker(
                        icon = fixedMarker.options.icon?.let {
                            getBitmapDescriptor(
                                context = LocalContext.current,
                                id = it
                            )
                        },
                        position = fixedMarker.position,
                        title = fixedMarker.options.title,
                        snippet = fixedMarker.options.snippet,
                    )
                }
            }

            startPoint.value?.let {
                Button(modifier = Modifier.align(Alignment.BottomCenter),
                    onClick = {
                        onSubmit(it)
                    }) {
                    Text(text = validationText)
                }
            }
        }

    }
}

val locationPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    listOf(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
} else {
    listOf("android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION")
}
