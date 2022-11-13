package fr.racomach.zigwheelo.onboarding.usecase

import app.cash.turbine.test
import arrow.core.Either
import com.benasher44.uuid.Uuid
import fr.racomach.api.cyclist.dto.CreateResponse
import fr.racomach.api.error.ErrorResponse
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers

class RegisterUsernameSpecs : ShouldSpec({

    context("Validation de l'étape enregistrement du nom de l'utilisateur") {
        val registerUsername = RegisterUsername(
            cyclistRepo = mockk {
                coEvery { createCyclist("Good username") } returnsMany listOf(
                    Either.Left(ErrorResponse("DUPLICATE", "Nom d'utilisateur déjà utilisé")),
                    Either.Right(CreateResponse(Uuid.randomUUID())),
                )
            },
            dispatcher = Dispatchers.Unconfined
        )
        registerUsername.run().test {
            should("doit demander le nom de l'utilisateur") {
                val state = awaitItem()
                state.shouldBeTypeOf<RegisterUsername.State.AskUsername>()
                state.callback.complete(null)
            }
            should("doit passer en état de validation") {
                awaitItem().shouldBeTypeOf<RegisterUsername.State.Validating>()
            }
            should("recevoir une erreur car le nom est obligatoire et demander à nouveau le username") {
                val state = awaitItem()
                state.shouldBeTypeOf<RegisterUsername.State.AskUsername>()
                state.error shouldNotBe null
                state.callback.complete("abc")
            }
            should("doit passer en état de validation") {
                awaitItem().shouldBeTypeOf<RegisterUsername.State.Validating>()
            }
            should("recevoir une erreur car le nom est trop court et demander à nouveau le username") {
                val state = awaitItem()
                state.shouldBeTypeOf<RegisterUsername.State.AskUsername>()
                state.error shouldNotBe null
                state.callback.complete("Good username")
            }
            should("doit passer en état de validation") {
                awaitItem().shouldBeTypeOf<RegisterUsername.State.Validating>()
            }
            should("recevoir une erreur du serveur et demander à nouveau le username") {
                val state = awaitItem()
                state.shouldBeTypeOf<RegisterUsername.State.AskUsername>()
                state.error shouldNotBe null
                state.callback.complete("Good username")
            }
            should("doit passer en état de validation") {
                awaitItem().shouldBeTypeOf<RegisterUsername.State.Validating>()
            }
            should("doit valider le username") {
                awaitItem().shouldBeTypeOf<RegisterUsername.State.Succeed>()
            }
            awaitComplete()
        }
    }
})