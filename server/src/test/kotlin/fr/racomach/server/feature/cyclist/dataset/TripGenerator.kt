package fr.racomach.server.feature.cyclist.dataset

import fr.racomach.Data
import fr.racomach.values.Location
import fr.racomach.values.Trip
import kotlinx.datetime.LocalTime
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

fun generateTrip(
    from: Location = Location(Data.fake.random.nextDouble(), Data.fake.random.nextDouble()),
    to: Location = Location(Data.fake.random.nextDouble(), Data.fake.random.nextDouble()),
    schedule: LocalTime = LocalTime(Data.fake.random.nextInt(0..12), Data.fake.random.nextInt(0..59)),
    duration: Duration = Data.fake.random.nextInt(0..120).minutes,
) = Trip(from, to, schedule, duration)