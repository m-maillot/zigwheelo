package common

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
fun ErrorText(text: String?) {
    if (text != null) {
        Span(attrs = { style { color(Color.red) } }) {
            Text(text)
        }
    }
}
