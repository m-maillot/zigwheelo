import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.benasher44.uuid.Uuid
import fr.racomach.api.ZigWheeloApi
import fr.racomach.api.cyclist.usecase.AddTrip
import org.jetbrains.compose.web.css.*
import trip.AddTripForm

@Composable
fun CreateTrip(cyclistId: Uuid) {

    val addTrip = AddTrip(ZigWheeloApi.create("http://localhost:9580", true))
    val state = addTrip.observeState().collectAsState()

    AddTripForm { }
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