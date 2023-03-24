package fr.racomach.zigwheelo.onboarding.ui.component

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.permissions.*
import fr.racomach.api.onboard.usecase.SettingStepState
import fr.racomach.zigwheelo.R
import fr.racomach.zigwheelo.common.component.TimePicker
import fr.racomach.zigwheelo.ui.theme.ZigWheeloTypography
import fr.racomach.zigwheelo.ui.theme.ZigwheeloTheme3
import kotlinx.datetime.LocalTime
import java.util.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SettingsStep(
    modifier: Modifier = Modifier,
    state: SettingStepState,
    onAcceptNotification: (time: LocalTime) -> Unit,
    onDenyNotification: () -> Unit,
) {
    val notificationPermissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(postNotification)
    } else {
        grantedPermission
    }

    val timeSelected = remember { mutableStateOf(LocalTime(7, 30)) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = "Notification",
            style = ZigWheeloTypography.displayMedium,
        )
        NotificationPicture(Modifier.size(180.dp))
        Text(
            textAlign = TextAlign.Center,
            text = "Soyez notifié d'un changement de météo pour vos trajets afin de mieux préparer votre journée"
        )
        when (notificationPermissionState.status) {
            is PermissionStatus.Denied -> {
                // if (notificationPermissionState.status.shouldShowRationale) {
                Button(onClick = { notificationPermissionState.launchPermissionRequest() }) {
                    Text(text = "Autoriser les notifications")
                }
                // } else {
                //    Text(text = "Vous ne souhaitez pas recevoir de notification")
                //    Button(onClick = { onDenyNotification() }, enabled = !state.loading) {
                //        Text("Continuer")
                //    }
                //}
            }
            PermissionStatus.Granted -> {
                TimePicker(
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(
                            modifier = Modifier.padding(bottom = 8.dp),
                            text = "Recevoir la notification vers :"
                        )
                    },
                    onChange = { timeSelected.value = it }
                )
                Button(
                    onClick = { onAcceptNotification(timeSelected.value) },
                    enabled = !state.loading,
                ) {
                    Text("Valider")
                }
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
private fun NotificationPicture(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(if (isSystemInDarkTheme()) R.raw.notification_dark else R.raw.notification_light)
    )
    LottieAnimation(
        modifier = modifier,
        composition = composition,
    )
}

val postNotification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    android.Manifest.permission.POST_NOTIFICATIONS
} else {
    "android.permission.POST_NOTIFICATIONS"
}

@OptIn(ExperimentalPermissionsApi::class)
val grantedPermission = object : PermissionState {
    override val permission: String
        get() = postNotification
    override val status: PermissionStatus
        get() = PermissionStatus.Granted

    override fun launchPermissionRequest() {
        // Do notihng
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.NEXUS_5X,
)
@Composable
private fun SettingsStepPreview() {
    ZigwheeloTheme3 {
        SettingsStep(
            state = SettingStepState(),
            onAcceptNotification = {},
            onDenyNotification = {}
        )
    }
}