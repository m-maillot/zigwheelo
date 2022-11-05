import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.benasher44.uuid.Uuid
import fr.racomach.api.ZigWheeloApi
import fr.racomach.api.cyclist.usecase.CreateCyclist
import fr.racomach.api.cyclist.usecase.CreateCyclistAction
import fr.racomach.api.cyclist.usecase.CreateCyclistState
import kotlinx.browser.window
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Text

@Composable
fun RegisterForm(onCreated: (id: Uuid) -> Unit) {
    val create = CreateCyclist(ZigWheeloApi.create("http://localhost:9580", true))

    val usernameState = remember { mutableStateOf("") }
    val state = create.observeState().collectAsState()

    Div(attrs = { style { RegisterFormStyles.form } }) {
        Div(attrs = { style { RegisterFormStyles.formItem } }) {
            Input(type = InputType.Text) {
                value(usernameState.value)
                onInput { event -> usernameState.value = event.value }
            }
        }

        when (val currentState = state.value) {
            is CreateCyclistState.Created -> {
                window.localStorage.setItem("cyclistID", currentState.id.toString())
                onCreated(currentState.id)
            }
            CreateCyclistState.Creating -> {
                Div(attrs = { style { RegisterFormStyles.formItem } }) {
                    Button(
                        attrs = {
                            disabled()
                        }
                    ) {
                        Text("Création en cours...")
                    }
                }
            }
            is CreateCyclistState.Error, CreateCyclistState.Pending -> {
                if (currentState is CreateCyclistState.Error) {
                    Div(attrs = { style { RegisterFormStyles.formItem } }) {
                        Text("Erreur: ${currentState.detail} (${currentState.type})")
                    }
                }
                Div(attrs = { style { RegisterFormStyles.formItem } }) {
                    Button(
                        attrs = {
                            onClick {
                                create.dispatch(CreateCyclistAction.Create(usernameState.value))
                            }
                        }
                    ) {
                        Text("Valider")
                    }
                }
            }
        }

    }
}

private object RegisterFormStyles : StyleSheet() {

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