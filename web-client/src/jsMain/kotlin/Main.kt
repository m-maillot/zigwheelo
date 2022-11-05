import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.benasher44.uuid.uuidFrom
import kotlinx.browser.window
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable(rootElementId = "root") {
        val cyclistId = remember {
            mutableStateOf(window.localStorage.getItem("cyclistID")?.let { uuidFrom(it) })
        }

        if (cyclistId.value == null) {
            RegisterForm {
                cyclistId.value = it
            }
        } else {
            Text("User : ${cyclistId.value}")
            CreateTrip(cyclistId.value!!)
        }
    }
}
