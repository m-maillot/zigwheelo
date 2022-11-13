package fr.racomach.zigwheelo.onboarding.ui.component

import android.app.TimePickerDialog
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.permissions.*
import fr.racomach.zigwheelo.R
import fr.racomach.zigwheelo.ui.theme.ZigWheeloTypography
import fr.racomach.zigwheelo.ui.theme.ZigwheeloTheme3
import java.util.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SettingsStep(
    modifier: Modifier = Modifier,
    onAcceptNotification: (time: Pair<Int, Int>) -> Unit,
) {
    val cameraPermissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(
            android.Manifest.permission.POST_NOTIFICATIONS
        )
    } else {
        object : PermissionState {
            override val permission: String
                get() = ""
            override val status: PermissionStatus
                get() = PermissionStatus.Granted

            override fun launchPermissionRequest() {
                // Do notihng
            }

        }
    }

    val timeSelected = remember { mutableStateOf(7 to 30) }

    val timePickerDialog = TimePickerDialog(
        LocalContext.current,
        { _, hour: Int, minute: Int ->
            timeSelected.value = hour to minute
        }, timeSelected.value.first, timeSelected.value.second, false
    )

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
        if (cameraPermissionState.status.isGranted) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Recevoir la notification vers :")
                Text(
                    text = "${
                        timeSelected.value.first.toString().padStart(2, '0')
                    }:${timeSelected.value.second.toString().padStart(2, '0')}",
                    style = ZigWheeloTypography.bodyLarge,
                )
                OutlinedButton(onClick = { timePickerDialog.show() }) {
                    Text("Changer l'heure")
                }
            }
            Button(onClick = { onAcceptNotification(timeSelected.value) }) {
                Text("Valider")
            }
        } else {
            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                Text(text = "Autoriser les notifications")
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

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.NEXUS_5X,
)
@Composable
private fun SettingsStepPreview() {
    ZigwheeloTheme3 {
        SettingsStep(onAcceptNotification = {})
    }
}