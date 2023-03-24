package fr.racomach.zigwheelo.common.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberField(
    modifier: Modifier = Modifier,
    inputWidth: Dp? = null,
    defaultValue: Int? = null,
    label: @Composable ColumnScope.() -> Unit,
    textStyle: TextStyle = LocalTextStyle.current,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        autoCorrect = false,
        keyboardType = KeyboardType.Number
    ),
    keyboardActions: KeyboardActions = KeyboardActions(),
    onChange: (value: Int?) -> Unit,
) {

    var input by remember {
        mutableStateOf(defaultValue)
    }

    Column(modifier = modifier.fillMaxWidth()) {
        label()
        Row(
            modifier = Modifier.apply {
                if (inputWidth != null) {
                    width(inputWidth)
                }
            },
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = "${input ?: ""}",
                textStyle = textStyle,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                onValueChange = {
                    input = it.toInt()
                    onChange(input)
                }
            )
        }
    }
}

private val NON_NUMERIC_REGEX = "\\D".toRegex()

private fun String.ignoreNonNumericCharacter() = NON_NUMERIC_REGEX.replace(this, "")
private fun String.toInt(): Int? =
    ignoreNonNumericCharacter().ifEmpty { null }?.toIntOrNull()

@Preview(
    showBackground = true,
    device = Devices.NEXUS_5X,
)
@Composable
private fun NumberFieldPreview() {
    Column(
        modifier = Modifier
            .width(500.dp)
            .height(300.dp)
    ) {
        NumberField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            label = { Text("Indiquer un nombre :") },
            onChange = {},
        )
    }
}
