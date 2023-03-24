package fr.racomach.zigwheelo.common.component

import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.racomach.zigwheelo.ui.theme.ZigWheeloTypography
import fr.racomach.zigwheelo.ui.theme.ZigwheeloTheme3
import kotlinx.datetime.LocalTime

@Composable
fun TimePickerSmall(
    modifier: Modifier = Modifier,
    defaultValue: LocalTime = LocalTime(7, 30),
    onChange: (LocalTime) -> Unit,
) {
    val timeSelected = remember { mutableStateOf(defaultValue) }

    val timePickerDialog = TimePickerDialog(
        LocalContext.current,
        { _, hour: Int, minute: Int ->
            timeSelected.value = LocalTime(hour, minute)
            onChange(timeSelected.value)
        }, timeSelected.value.hour, timeSelected.value.minute, true
    )

    Row(
        modifier = modifier.clickable { timePickerDialog.show() }
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = timeSelected.value.hour.toString().padStart(2, '0'),
            style = ZigWheeloTypography.displayLarge
        )
        Text(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            text = ":",
            style = ZigWheeloTypography.displayLarge
        )
        Text(
            modifier = Modifier.padding(8.dp),
            text = timeSelected.value.minute.toString().padStart(2, '0'),
            style = ZigWheeloTypography.displayLarge
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun TimePickerSmallPreview() {
    ZigwheeloTheme3 {
        TimePickerSmall(onChange = {})
    }
} 