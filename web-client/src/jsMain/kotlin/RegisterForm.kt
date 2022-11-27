import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import fr.racomach.api.onboard.usecase.WelcomeStepState
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Text

@Composable
fun RegisterForm(
    state: WelcomeStepState,
    onSubmit: (username: String?) -> Unit
) {

    val usernameState = remember { mutableStateOf("") }

    Div(attrs = { style { RegisterFormStyles.form } }) {
        Div(attrs = { style { RegisterFormStyles.formItem } }) {
            Input(type = InputType.Text) {
                value(usernameState.value)
                onInput { event -> usernameState.value = event.value }
            }
        }

        state.error?.let { error ->
            Div(attrs = { style { RegisterFormStyles.formItem } }) {
                Text("Erreur: ${error.message} (${error.type})")
            }
        }

        Div(attrs = { style { RegisterFormStyles.formItem } }) {
            Button(
                attrs = {
                    onClick { onSubmit(usernameState.value) }
                    if (state.loading) disabled()
                }
            ) {
                Text("Valider")
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