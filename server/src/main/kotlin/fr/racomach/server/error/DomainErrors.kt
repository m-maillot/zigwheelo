package fr.racomach.server.error

import fr.racomach.event.sourcing.Error
import kotlinx.datetime.LocalTime

sealed class DomainError(
    open val type: String,
    override val message: String
) : Error() {

    sealed class Generic(
        override val type: String,
        override val message: String
    ) : DomainError(type, message) {
        object NotFound : Generic("NotFound", "Aggregate non trouvé")
    }

    sealed class Cyclist(
        override val type: String,
        override val message: String
    ) : DomainError(type, message) {
        data class UsernameAlreadyTaken(val username: String) :
            Cyclist("UsernameAlreadyTaken", "Le pseudo $username est déjà utilisé")

        object AlreadyCreated : Cyclist("AlreadyCreated", "Cycliste déjà enregistré")
        data class DuplicateTrip(val existingTripLocalDate: LocalTime) :
            Cyclist(
                "DuplicateTrip",
                "Le trajet est en conflit avec celui de ${existingTripLocalDate.hour}:${existingTripLocalDate.minute}"
            )
    }

    sealed class Place(
        override val type: String,
        override val message: String,
    ) : DomainError(type, message) {
        data class Network(override val message: String) :
            Place("NetworkIssue", "Erreur réseau: $message")
    }
}