package fr.racomach.zigwheelo.onboarding.ui.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import fr.racomach.api.onboard.usecase.WelcomeStepState
import fr.racomach.zigwheelo.R
import fr.racomach.zigwheelo.ui.theme.ZigWheeloTypography
import fr.racomach.zigwheelo.ui.theme.ZigwheeloTheme3

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
            text = "Votre profil", style = ZigWheeloTypography.displayMedium,
        )
        if (state.loading) {
            SmileyHappy(Modifier.size(150.dp))
        } else if (state.username != null) {
            SmileyWink(Modifier.size(150.dp))
        } else {
            if (state.error != null) {
                SmileySad(Modifier.size(150.dp))
            } else {
                SmileyHappy(Modifier.size(150.dp))
            }
        }
        if (state.username != null) {
            Text(text = "Bienvenue ${state.username} !", style = ZigWheeloTypography.headlineLarge)
        } else {
            UsernameInput(
                onSubmit = onSubmit,
                error = state.error?.message,
                enabled = !state.loading
            )
        }
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

@Preview(
    showBackground = true,
    device = Devices.NEXUS_6
)
@Composable
private fun UsernameStepPreview() {
    ZigwheeloTheme3 {
        Box(modifier = Modifier.fillMaxSize()) {
            UsernameStep(
                modifier = Modifier.fillMaxSize(),
                state = WelcomeStepState(),
                onSubmit = {}
            )
        }
    }
}

@Preview(
    showBackground = true,
    device = Devices.NEXUS_6
)
@Composable
private fun UsernameStepPreviewLoading() {
    ZigwheeloTheme3 {
        Box(modifier = Modifier.fillMaxSize()) {
            UsernameStep(
                modifier = Modifier.fillMaxSize(),
                state = WelcomeStepState(loading = true),
                onSubmit = {}
            )
        }
    }
}


@Preview(
    showBackground = true,
    device = Devices.NEXUS_6
)
@Composable
private fun UsernameStepPreviewSucceed() {
    ZigwheeloTheme3 {
        Box(modifier = Modifier.fillMaxSize()) {
            UsernameStep(
                modifier = Modifier.fillMaxSize(),
                state = WelcomeStepState(username = "Pseudo"),
                onSubmit = {}
            )
        }
    }
}