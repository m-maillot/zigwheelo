package trip

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import common.ErrorText
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.required
import org.jetbrains.compose.web.attributes.step
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

@Composable
fun AddTripForm(onSubmit: () -> Unit) {

    val startLatitudeState = remember { mutableStateOf<Number?>(null) }
    val startLatitudeErrorState = remember { mutableStateOf<String?>(null) }
    val startLongitudeState = remember { mutableStateOf<Number?>(null) }
    val endLatitudeState = remember { mutableStateOf<Number?>(null) }
    val endLongitudeState = remember { mutableStateOf<Number?>(null) }
    val scheduleState = remember { mutableStateOf<String?>(null) }
    val roundTripStart = remember { mutableStateOf<String?>(null) }
    val duration = remember { mutableStateOf<Number?>(null) }
    val name = remember { mutableStateOf<String?>(null) }

    Div(attrs = { style { CreateTripFormStyles.form } }) {
        Div(attrs = { style { CreateTripFormStyles.formItem } }) {
            Text("Name: ")
            Input(type = InputType.Text) {
                value(name.value ?: "")
                onInput { event -> name.value = event.value }
            }
            ErrorText(startLatitudeErrorState.value)
        }
        Div(attrs = { style { CreateTripFormStyles.formItem } }) {
            Div { Text("Départ") }
            Div {
                Text("Latitude: ")
                Input(type = InputType.Number) {
                    value(startLatitudeState.value ?: 0)
                    onInput { event -> startLatitudeState.value = event.value }
                    step(0.1)
                    required()
                }
            }
            Div {
                Text("Longitude: ")
                Input(type = InputType.Number) {
                    value(startLongitudeState.value ?: 0)
                    onInput { event -> startLongitudeState.value = event.value }
                    step(0.1)
                    required()
                }
            }
        }
        Div(attrs = { style { CreateTripFormStyles.formItem } }) {
            Div {
                Text("Arrivée")
            }
            Div {
                Text("Latitude: ")
                Input(type = InputType.Number) {
                    value(endLatitudeState.value ?: 0)
                    onInput { event -> endLatitudeState.value = event.value }
                    step(0.1)
                    required()
                }
            }
            Div {
                Text("Longitude: ")
                Input(type = InputType.Number) {
                    value(endLongitudeState.value ?: 0)
                    onInput { event -> endLongitudeState.value = event.value }
                    step(0.1)
                    required()
                }
            }
        }
        Div(attrs = { style { CreateTripFormStyles.formItem } }) {
            Text("Heure du trajet: ")
            Input(type = InputType.Time) {
                value(scheduleState.value ?: "")
                onInput { event -> scheduleState.value = event.value }
                required()
            }
        }
        Div(attrs = { style { CreateTripFormStyles.formItem } }) {
            Text("Duration (en minutes): ")
            Input(type = InputType.Number) {
                value(duration.value ?: 0)
                onInput { event -> duration.value = event.value }
                step(1)
                required()
            }
        }
        Div(attrs = { style { CreateTripFormStyles.formItem } }) {
            Text("Heure du retour (s'il y a): ")
            Input(type = InputType.Time) {
                value(roundTripStart.value ?: "")
                onInput { event -> roundTripStart.value = event.value }
                required()
            }
        }
        Div(attrs = { style { CreateTripFormStyles.formItem } }) {
            Button(
                attrs = {
                    onClick {
                        if (startLatitudeErrorState.value == null) {
                            startLatitudeErrorState.value = "Ce champs est obligatoire"
                        }
                        // TODO create.dispatch(CreateCyclistAction.Create(usernameState.value))
                    }
                }
            ) {
                Text("Valider")
            }
        }
    }
}


private object CreateTripFormStyles : StyleSheet() {

    val form by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        flexWrap(FlexWrap.Nowrap)
        justifyContent(JustifyContent.Center)
        alignItems(AlignItems.Normal)
        alignContent(AlignContent.Center)
    }

    val formItem by style {
        display(DisplayStyle.Block)
        flexGrow(0)
        flexShrink(0)
        flexBasis("auto")
        alignSelf(AlignSelf.Auto)
        order(0)
    }
}