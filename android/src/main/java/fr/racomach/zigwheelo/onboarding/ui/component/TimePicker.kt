package fr.racomach.zigwheelo.onboarding.ui.component

import android.app.TimePickerDialog
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(modifier = Modifier.padding(bottom = 8.dp), text = "Recevoir la notification vers :")
        Row(
            modifier = Modifier
                .border(
                    width = 3.dp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    shape = RoundedCornerShape(8.dp)
                )
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
        OutlinedButton(onClick = { timePickerDialog.show() }) {
            Text("Changer l'heure")
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun TimePickerPreview() {
    ZigwheeloTheme3 {
        TimePicker(onChange = {})
    }
} 