package fr.racomach.event.sourcing

import fr.racomach.event.sourcing.command.TestCommand
import jdk.jshell.spi.ExecutionControl.NotImplementedException

val testSelector: AggregateSelector = AggregateSelector {
    when (it) {
        is TestCommand -> AggregateTest()
        else -> throw NotImplementedException("")
    }
}