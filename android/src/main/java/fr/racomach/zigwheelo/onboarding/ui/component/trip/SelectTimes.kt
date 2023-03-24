package fr.racomach.zigwheelo.onboarding.ui.component.trip

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import fr.racomach.zigwheelo.R
import fr.racomach.zigwheelo.common.component.NumberField
import fr.racomach.zigwheelo.common.component.TimePicker
import fr.racomach.zigwheelo.ui.theme.ZigwheeloTheme3
import kotlinx.datetime.LocalTime

@Composable
fun SelectTimes(
    modifier: Modifier = Modifier,
    onSubmit: (startAt: LocalTime, durationMinutes: Int, roundTripAt: LocalTime) -> Unit
) {
    val startAt = remember { mutableStateOf<LocalTime?>(null) }
    val durationMinutes = remember { mutableStateOf<Int?>(null) }
    val roundTripAt = remember { mutableStateOf<LocalTime?>(null) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        Picture(modifier = Modifier.size(100.dp))
        TimePicker(
            label = {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = "Vous partez vers :"
                )
            },
            onChange = { startAt.value = it }
        )
        NumberField(
            modifier = Modifier.fillMaxWidth(),
            inputWidth = 200.dp,
            label = {
                Box(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Votre trajet dure (en minutes) :")
                }

            },
            onChange = {
                durationMinutes.value = it
            }
        )
        TimePicker(
            label = {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = "Vous effectuez le trajet retour à :"
                )
            },
            onChange = { roundTripAt.value = it }
        )
        Button(onClick = {
            onSubmit(
                startAt.value!!,
                durationMinutes.value!!,
                roundTripAt.value!!
            )
        }) {
            Text(text = "Valider")
        }
    }
}

@Composable
private fun Picture(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(if (isSystemInDarkTheme()) R.raw.clock_midnight_dark else R.raw.clock_midnight_light)
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
private fun SelectTimesPreview() {
    ZigwheeloTheme3 {
        SelectTimes(onSubmit = { _, _, _ -> })
    }
}