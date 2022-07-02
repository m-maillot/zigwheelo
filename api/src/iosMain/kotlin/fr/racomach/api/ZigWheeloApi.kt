package fr.racomach.api

fun ZigWheeloApi.Companion.create(baseUrl: String, withLog: Boolean) = ZigWheeloApi(
    IosHttpClient(withLog),
    baseUrl,
)