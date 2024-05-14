package org.incava.mmonkeys.exec

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.MonkeyFactory
import org.incava.mmonkeys.match.Matcher

class SimulationParams<T>(
    private val numMonkeys: Int,
    val sought: T,
    val matcher: (Monkey, T) -> Matcher,
    private val typewriterFactory: TypewriterFactory = TypewriterFactory(),
    val showMemory: Boolean = false,
) {
    fun summarize() {
        Console.info("# monkeys", numMonkeys)
        Console.info("sought", sought)
        Console.info("matcher", matcher)
        Console.info("showMemory", showMemory)
        Console.info("typewriterFactory", typewriterFactory)
    }

    fun makeMonkeys(): List<Monkey> {
        val monkeyFactory = MonkeyFactory { typewriterFactory.create() }
        // I don't make monkeys; I just train them!
        return (0 until numMonkeys).map { monkeyFactory.createMonkey(id = it) }
    }

    override fun toString(): String {
        return "SimulationParams(numMonkeys=$numMonkeys, sought=$sought, matcher=$matcher, typewriterFactory=$typewriterFactory, showMemory=$showMemory)"
    }
}