package org.incava.mmonkeys.testutil

import org.incava.ikdk.io.Console

class Trial(private vararg val options: InvokeTrial) {
    private val trialInvokes = 10

    fun run() {
        val trialsList = options.toList()
        repeat(trialInvokes) {
            trialsList.shuffled().forEach { it.run() }
        }
    }

    fun summarize() {
        options.forEach {
            Console.info("${it.name} - average", it.durations.average())
            Console.info("${it.name} - durations", it.durations)
        }
    }
}
