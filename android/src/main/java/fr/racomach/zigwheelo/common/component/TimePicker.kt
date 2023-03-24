package fr.racomach.zigwheelo.common.component

import android.app.TimePickerDialog
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.racomach.zigwheelo.ui.theme.ZigWheeloTypography
import fr.racomach.zigwheelo.ui.theme.ZigwheeloTheme3
import kotlinx.datetime.LocalTime

@Composable
fun TimePicker(
    modifier: Modifier = Modifier,
    label: @Composable ColumnScope.() -> Unit,
    defaultValue: LocalTime? = null,
    onChange: (LocalTime) -> Unit,
) {
    val timeSelected = remember { mutableStateOf(defaultValue) }
    val error = remember { mutableStateOf<String?>(null) }

    val timePickerDialog = TimePickerDialog(
        LocalContext.current,
        { _, hour: Int, minute: Int ->
            timeSelected.value = LocalTime(hour, minute)
            onChange(LocalTime(hour, minute))
        }, timeSelected.value?.hour ?: 12, timeSelected.value?.minute ?: 0, true
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        label()
        Row(
            modifier = Modifier
                .defaultMinSize(minWidth = 200.dp)
                .border(
                    width = 3.dp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { timePickerDialog.show() },
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = timeSelected.value?.hour?.toString()?.padStart(2, '0') ?: "--",
                style = ZigWheeloTypography.displayLarge
            )
            Text(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                text = ":",
                style = ZigWheeloTypography.displayLarge
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = timeSelected.value?.minute?.toString()?.padStart(2, '0') ?: "--",
                style = ZigWheeloTypography.displayLarge
            )
        }
        error.value?.let {
            Text(text = "Erreur: $it")
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun TimePickerPreview() {
    ZigwheeloTheme3 {
        Column {
            TimePicker(
                label = {
                    Text(
                        modifier = Modifier.padding(bottom = 8.dp),
                        text = "Recevoir la notification vers :"
                    )
                }, onChange = {})
            TimePicker(
                label = {
                    Text(
                        modifier = Modifier.padding(bottom = 8.dp),
                        text = "Recevoir la notification vers :"
                    )
                }, onChange = {}, defaultValue = LocalTime(7, 30)
            )
            TimePicker(
                label = {
                    Text(
                        modifier = Modifier.padding(bottom = 8.dp),
                        text = "Recevoir la notification vers :"
                    )
                }, onChange = {}, defaultValue = LocalTime(14, 59)
            )
        }
    }
} 