package fr.racomach.zigwheelo.onboarding.ui.component.trip

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import fr.racomach.zigwheelo.R
import fr.racomach.zigwheelo.onboarding.ui.component.PagerDot
import fr.racomach.zigwheelo.ui.theme.ZigWheeloTypography

@Composable
fun NoTripFilled(
    modifier: Modifier = Modifier,
    onNewTrip: () -> Unit,
    onSkip: () -> Unit,
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
        TripPicture(Modifier.size(180.dp))
        Text(
            textAlign = TextAlign.Center,
            text = "Vous pouvez définir un trajet régulier à vélo. Ainsi vous recevrez chaque jour une notification pour vous aider à bien vous équipez pour la journée !"
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = onNewTrip) {
                Text(text = "Définir un trajet")
            }
            OutlinedButton(onClick = onSkip) {
                Text(text = "Passer")
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
private fun TripPicture(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(if (isSystemInDarkTheme()) R.raw.trip_dark else R.raw.trip_light)
    )
    LottieAnimation(
        modifier = modifier,
        composition = composition,
    )
}