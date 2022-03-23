import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import fr.racomach.api.ZigWheeloApi
import fr.racomach.api.usecase.SearchParkAction
import fr.racomach.api.usecase.SearchParkState
import fr.racomach.api.usecase.SearchParks
import kotlinx.coroutines.InternalCoroutinesApi
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable

@InternalCoroutinesApi
fun main() {

    val searchParks = SearchParks(ZigWheeloApi.create("http://localhost:8080"))

    renderComposable(rootElementId = "root") {
        Style(TextStyles)

        val state = searchParks.observeState().value
        val latitude = remember { mutableStateOf<String>("45.742989978188945") }

        LaunchedEffect(true) {
            searchParks.dispatch(
                SearchParkAction.Search(
                    45.742989978188945,
                    4.851021720981201,
                    500
                )
            )
        }

        Div(attrs = { style { padding(16.px) } }) {
            Div(attrs = { style { border { color(rgb(23, 24, 28)) } } }) {
                TextInput(value = latitude.value) {
                    onInput { event -> latitude.value = event.value }
                }
            }

            H1(attrs = { classes(TextStyles.titleText) }) {
                Text("Résultats:")
            }

            when (state) {
                is SearchParkState.Error -> {
                    H1 { Text("Error: ${state.detail}") }
                }
                is SearchParkState.Loaded -> {
                    state.parks.forEach { park ->
                        Div(
                            attrs = {
                                style {
                                    display(DisplayStyle.Flex)
                                    alignItems(AlignItems.Center)
                                }
                            }
                        ) {

                            Span(attrs = { classes(TextStyles.personText) }) {
                                Text("${park.address} (${park.location.latitude}/${park.location.longitude})")
                            }
                        }
                    }
                }
                SearchParkState.Loading -> {
                    H1 { Text("Loading") }
                }
            }

        }
    }
}

object TextStyles : StyleSheet() {

    val titleText by style {
        color(rgb(23, 24, 28))
        fontSize(50.px)
        property("font-size", 50.px)
        property("letter-spacing", (-1.5).px)
        property("font-weight", 900)
        property("line-height", 58.px)

        property(
            "font-family",
            "Gotham SSm A,Gotham SSm B,system-ui,-apple-system,BlinkMacSystemFont,Segoe UI,Roboto,Oxygen,Ubuntu,Cantarell,Droid Sans,Helvetica Neue,Arial,sans-serif"
        )
    }

    val personText by style {
        color(rgb(23, 24, 28))
        fontSize(24.px)
        property("font-size", 28.px)
        property("letter-spacing", "normal")
        property("font-weight", 300)
        property("line-height", 40.px)

        property(
            "font-family",
            "Gotham SSm A,Gotham SSm B,system-ui,-apple-system,BlinkMacSystemFont,Segoe UI,Roboto,Oxygen,Ubuntu,Cantarell,Droid Sans,Helvetica Neue,Arial,sans-serif"
        )
    }
}