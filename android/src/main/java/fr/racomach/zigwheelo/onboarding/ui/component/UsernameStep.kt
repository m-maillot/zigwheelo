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
import fr.racomach.zigwheelo.R
import fr.racomach.zigwheelo.onboarding.ui.OnboardingViewModel.State.Username
import fr.racomach.zigwheelo.ui.theme.ZigWheeloTypography

@Composable
fun UsernameStep(
    modifier: Modifier = Modifier,
    state: Username,
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
        when (state) {
            Username.Loading -> SmileyHappy(Modifier.size(150.dp))
            is Username.Pending ->
                if (state.error != null) SmileySad(Modifier.size(150.dp))
                else SmileyHappy(Modifier.size(150.dp))
            Username.Succeed -> SmileyWink(Modifier.size(150.dp))
        }
        UsernameInput(
            onSubmit = (state as? Username.Pending)?.onSubmit ?: {},
            error = (state as? Username.Pending)?.error,
            enabled = state is Username.Pending
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