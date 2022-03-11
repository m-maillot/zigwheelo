package fr.racomach.zigwheelo.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun ZigWheeloTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        content = content
    )
}