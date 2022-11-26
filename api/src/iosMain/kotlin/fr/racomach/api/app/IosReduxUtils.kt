@file:Suppress("unused")

package fr.racomach.api.app

import fr.racomach.api.onboard.usecase.OnboardUser
import fr.racomach.api.usecase.SearchParks

fun SearchParks.watchState() = observeState().wrap()
fun SearchParks.watchSideEffect() = observeSideEffect().wrap()

fun OnboardUser.watchState() = observeState().wrap()
fun OnboardUser.watchSideEffect() = observeSideEffect().wrap()