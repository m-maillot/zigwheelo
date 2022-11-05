import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.benasher44.uuid.Uuid
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.required
import org.jetbrains.compose.web.attributes.step
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

@Composable
fun CreateTrip(cyclistId: Uuid) {
    val startLatitudeState = remember { mutableStateOf<Number?>(null) }
    val startLongitudeState = remember { mutableStateOf<Number?>(null) }
    val endLatitudeState = remember { mutableStateOf<Number?>(null) }
    val endLongitudeState = remember { mutableStateOf<Number?>(null) }
    val scheduleState = remember { mutableStateOf<String?>(null) }
    val duration = remember { mutableStateOf<Number?>(null) }
    val name = remember { mutableStateOf<String?>(null) }

    Div(attrs = { style { CreateTripFormStyles.form } }) {
/*
val from: Location,
    val to: Location,
    val schedule: LocalTime,
    @Serializable(with = DurationSerializer::class)
    val duration: Duration,
    val roundTripStart: LocalTime? = null,
    val name: String? = null,
 */
        Form {
            Div(attrs = { style { CreateTripFormStyles.formItem } }) {
                Text("Name: ")
                Input(type = InputType.Text) {
                    value(name.value ?: "")
                    onInput { event -> name.value = event.value }
                }
            }
            Div(attrs = { style { CreateTripFormStyles.formItem } }) {
                Div { Text("Aller") }
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
                    Text("Retour")
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
                Text("Schedule: ")
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
                Button(
                    attrs = {
                        onClick {
                            // TODO create.dispatch(CreateCyclistAction.Create(usernameState.value))
                        }
                    }
                ) {
                    Text("Valider")
                }
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