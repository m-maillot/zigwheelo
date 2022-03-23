import io.ktor.client.*
import io.ktor.client.engine.darwin.*

internal fun IosHttpClient() = HttpClient(Darwin) {
    engine {
        configureRequest {
            setAllowsCellularAccess(true)
        }
    }
}