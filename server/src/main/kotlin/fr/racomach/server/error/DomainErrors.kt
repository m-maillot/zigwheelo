package fr.racomach.server.error

import kotlinx.datetime.LocalTime

sealed class DomainError(override val message: String) : Exception(message) {

    sealed class Generic(override val message: String) : DomainError(message) {
        object NotFound : Generic("Aggregate non trouvé")
    }

    sealed class Cyclist(override val message: String) : DomainError(message) {
        data class UsernameAlreadyTaken(val username: String) : Cyclist("Le pseudo $username est déjà utilisé")
        object AlreadyCreated : Cyclist("Cycliste déjà enregistré")
        data class DuplicateTrip(val existingTripLocalDate: LocalTime) :
            Cyclist("Le trajet est en conflit avec celui de ${existingTripLocalDate.hour}:${existingTripLocalDate.minute}")
    }
}