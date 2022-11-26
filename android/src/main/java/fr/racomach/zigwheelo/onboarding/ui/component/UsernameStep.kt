package fr.racomach.zigwheelo.onboarding.ui.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import fr.racomach.api.onboard.usecase.WelcomeStepState
import fr.racomach.zigwheelo.R
import fr.racomach.zigwheelo.ui.theme.ZigWheeloTypography

@Composable
fun UsernameStep(
    modifier: Modifier = Modifier,
    state: WelcomeStepState,
    onSubmit: (String?) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = "Bienvenue !", style = ZigWheeloTypography.displayMedium,
        )
        if (state.loading) {
            SmileyHappy(Modifier.size(150.dp))
        } else if (state.id != null) {
            SmileyWink(Modifier.size(150.dp))
        } else {
            if (state.error != null) {
                SmileySad(Modifier.size(150.dp))
            } else {
                SmileyHappy(Modifier.size(150.dp))
            }
        }
        UsernameInput(
            onSubmit = onSubmit,
            error = state.error?.message,
            enabled = !state.loading
        )
        PagerDot(
            count = 3,
            active = 1,
            activeColor = MaterialTheme.colorScheme.primary,
            inactiveColor = MaterialTheme.colorScheme.secondary,
            dotSize = 16.dp
        )
    }
}


@Composable
private fun SmileyHappy(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(if (isSystemInDarkTheme()) R.raw.happy_dark else R.raw.happy_light)
    )
    LottieAnimation(
        modifier = modifier,
        composition = composition,
    )
}


@Composable
private fun SmileySad(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(if (isSystemInDarkTheme()) R.raw.sad_dark else R.raw.sad_light)
    )
    LottieAnimation(
        modifier = modifier,
        composition = composition,
    )
}


@Composable
private fun SmileyWink(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(if (isSystemInDarkTheme()) R.raw.wink_dark else R.raw.wink_light)
    )
    LottieAnimation(
        modifier = modifier,
        composition = composition,
    )
}