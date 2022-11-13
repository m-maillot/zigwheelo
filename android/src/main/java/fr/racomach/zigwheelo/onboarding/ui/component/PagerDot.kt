package fr.racomach.zigwheelo.onboarding.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fr.racomach.zigwheelo.ui.theme.ZigwheeloTheme3

@Composable
fun PagerDot(
    modifier: Modifier = Modifier,
    count: Int,
    active: Int,
    activeColor: Color,
    inactiveColor: Color,
    dotSize: Dp = 42.dp,
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        (0 until count).map {
            val dotSizePx = with(LocalDensity.current) { dotSize.toPx() }
            Box(
                modifier = Modifier
                    .size(dotSize + 16.dp)
                    .padding(8.dp)
            ) {
                Dot(
                    radius = dotSizePx / 2,
                    color = if (it < active) activeColor else inactiveColor
                )
            }
        }
    }
}

@Composable
fun Dot(modifier: Modifier = Modifier, radius: Float, color: Color) {
    Canvas(
        modifier = modifier
            // Hack of the day : https://stackoverflow.com/questions/64939726/jetpack-compose-canvas-blendmode-src-in-makes-even-background-transparent
            .graphicsLayer(alpha = .99f)
    ) {
        drawCircle(color = color, radius = radius, center = Offset(radius, radius))
    }
}

@Preview(
    showBackground = true,
    heightDp = 200,
    widthDp = 200,
)
@Composable
private fun PagerDotPreview() {
    ZigwheeloTheme3 {
        PagerDot(
            count = 3,
            active = 1,
            activeColor = Color.Green,
            inactiveColor = Color.Black,
            dotSize = 24.dp
        )
    }
}