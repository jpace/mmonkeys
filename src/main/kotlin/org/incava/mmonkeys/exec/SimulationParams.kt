package org.incava.mmonkeys.exec

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.MonkeyFactory
import org.incava.mmonkeys.match.Matcher

abstract class SimulationParams(private val numMonkeys: Int, val monkeyFactory: MonkeyFactory) {
    fun summarize() {
        Console.info("# monkeys", numMonkeys)
    }

    fun makeMonkeys(): List<Monkey> {
        // I don't make monkeys; I just train them!
        return (0 until numMonkeys).map { monkeyFactory.createMonkey(it) }
    }

    abstract fun matcher(monkey: Monkey): Matcher
}
