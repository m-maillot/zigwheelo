package common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.required
import org.jetbrains.compose.web.attributes.step
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
fun InputNumber(label: String, name: String, isRequired: Boolean, onInput: (Number?) -> Unit) {
    val state = remember { mutableStateOf<Number?>(null) }
    val error = remember { mutableStateOf<String?>(null) }
    Div {
        Span(attrs = { id(name) }) {
            Text(label)
        }
        Input(type = InputType.Number) {
            value(state.value ?: 0)
            onInput { event ->
                state.value = event.value
                if (event.value == null) {
                    error.value = "Ce champ est obligatoire"
                } else {
                    error.value = null
                }
            }
            step(1)
            if (isRequired) required()
        }

    }
}