import fr.racomach.api.ZigWheeloApi

fun ZigWheeloApi.Companion.create(baseUrl: String) = ZigWheeloApi(
    IosHttpClient(),
    baseUrl,
)