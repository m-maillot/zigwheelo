package fr.racomach.api.app

import fr.racomach.api.usecase.SearchParks


fun SearchParks.watchState() = observeState().wrap()
fun SearchParks.watchSideEffect() = observeSideEffect().wrap()