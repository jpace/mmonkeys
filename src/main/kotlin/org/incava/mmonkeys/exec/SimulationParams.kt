package org.incava.mmonkeys.exec

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.Monkeys
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.util.Console

typealias TypewriterCtor = (List<Char>) -> Typewriter

class SimulationParams<T>(
    private val numMonkeys: Int,
    val sought: T,
    val matcher: (Monkey, T) -> Matcher,
    private val typewriterFactory: TypewriterFactory = TypewriterFactory(),
    val showMemory: Boolean = false,
) {
    fun summarize() {
        Console.info("# monkeys", numMonkeys)
        Console.info("corpus", sought)
        Console.info("matcher", matcher)
        Console.info("showMemory", showMemory)
        Console.info("typewriterFactory", typewriterFactory)
    }

    fun makeMonkeys(): Monkeys<T> {
        // I don't make monkeys; I just train them!
        val typewriter = typewriterFactory.typewriter()
        val monkeyList = (0 until numMonkeys).map { Monkey(it, typewriter) }
        return Monkeys(monkeyList, sought, matcher, showMemory)
    }
}