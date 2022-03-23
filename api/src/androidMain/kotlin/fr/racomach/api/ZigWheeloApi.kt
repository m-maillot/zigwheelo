package fr.racomach.api

fun ZigWheeloApi.Companion.create(baseUrl: String) = ZigWheeloApi(
    AndroidHttpClient(),
    baseUrl,
)