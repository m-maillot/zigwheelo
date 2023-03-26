package fr.racomach.zigwheelo.common.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
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

    val openDialog = remember { mutableStateOf(false) }

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
                .clickable { openDialog.value = true },
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

    TimePickerModal(
        isOpen = openDialog.value,
        onSelect = {
            timeSelected.value = it
            openDialog.value = false
            if (it != null) {
                onChange(it)
            }
        },
        onCancel = { openDialog.value = false }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimePickerModal(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    onSelect: (LocalTime?) -> Unit,
    onCancel: () -> Unit,
) {
    if (isOpen) {
        val timePickerState = rememberTimePickerState(
            initialHour = 12,
            initialMinute = 0,
            is24Hour = true,
        )

        DatePickerDialog(
            onDismissRequest = onCancel,
            confirmButton = {
                TextButton(
                    onClick = {
                        onSelect(
                            LocalTime(
                                hour = timePickerState.hour,
                                minute = timePickerState.minute
                            )
                        )
                    },
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onCancel
                ) {
                    Text("Cancel")
                }
            }
        ) {
            TimePicker(
                modifier = modifier,
                state = timePickerState,
            )
        }
    }
}

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4,
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