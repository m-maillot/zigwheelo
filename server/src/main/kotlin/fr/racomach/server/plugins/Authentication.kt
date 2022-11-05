package fr.racomach.server.plugins

import fr.racomach.event.sourcing.command.CyclistId
import fr.racomach.server.repository.UserRepository
import io.ktor.http.auth.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.util.*
import io.ktor.utils.io.charsets.*
import org.koin.ktor.ext.inject

fun Application.configureAuthentication() {

    val userRepository: UserRepository by inject()

    install(Authentication) {
        custom("custom") {
            validate { userId ->
                userRepository.get(userId)?.let { AuthenticatedUser(userId) }
            }
        }
    }

}

data class AuthenticatedUser(val id: CyclistId) : Principal

private fun AuthenticationConfig.custom(
    name: String? = null,
    configure: CustomAuthenticationProvider.Config.() -> Unit
) {
    val provider =
        CustomAuthenticationProvider(CustomAuthenticationProvider.Config(name).apply(configure))
    register(provider)
}

private class CustomAuthenticationProvider(config: Config) : AuthenticationProvider(config) {

    val authenticationFunction = config.authenticationFunction

    override suspend fun onAuthenticate(context: AuthenticationContext) {
        val call = context.call
        val credentials = call.request.customAuthenticationCredentials()
        val principal = credentials?.let { authenticationFunction(call, it) }

        val cause = when {
            credentials == null -> AuthenticationFailedCause.NoCredentials
            principal == null -> AuthenticationFailedCause.InvalidCredentials
            else -> null
        }

        if (cause != null) {
            @Suppress("NAME_SHADOWING")
            context.challenge(custoAuthenticationChallengeKey, cause) { challenge, call ->
                call.respond(UnauthorizedResponse(customAuthChallenge()))
                challenge.complete()
            }
        }
        if (principal != null) {
            context.principal(principal)
        }
    }

    class Config(name: String?) : AuthenticationProvider.Config(name) {
        internal var authenticationFunction: AuthenticationFunction<CyclistId> = {
            throw NotImplementedError(
                "Basic auth validate function is not specified. Use basic { validate { ... } } to fix."
            )
        }

        /**
         * Sets a validation function that checks a specified [UserPasswordCredential] instance and
         * returns [UserIdPrincipal] in a case of successful authentication or null if authentication fails.
         */
        fun validate(body: suspend ApplicationCall.(CyclistId) -> Principal?) {
            authenticationFunction = body
        }
    }
}

private fun ApplicationRequest.customAuthenticationCredentials(): CyclistId? {
    when (val authHeader = parseAuthorizationHeader()) {
        is HttpAuthHeader.Single -> {
            if (!authHeader.authScheme.equals("ZigWheelo", ignoreCase = true)) {
                return null
            }

            return runCatching {
                CyclistId(authHeader.blob)
            }.getOrNull()
        }

        else -> return null
    }
}

private fun customAuthChallenge(): HttpAuthHeader.Parameterized =
    HttpAuthHeader.Parameterized(
        "Custom",
        LinkedHashMap()
    )

private val custoAuthenticationChallengeKey: Any = "ZigWheelo"

