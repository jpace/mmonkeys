package org.incava.mmonkeys.exec

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.Monkeys
import org.incava.mmonkeys.match.Corpus
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.type.StandardTypewriter
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.util.Console

typealias TypewriterCtor = (List<Char>) -> Typewriter
typealias MatcherCtor = (Monkey, Corpus) -> Matcher

class SimulationParams(
    private val numMonkeys: Int,
    val sought: Corpus,
    val matcher: MatcherCtor,
    private val typewriterFactory: TypewriterFactory = TypewriterFactory(),
    val showMemory: Boolean = false,
) {
    fun summarize() {
        val whence = "SimulationParams"
        Console.info(whence, "# monkeys", numMonkeys)
        Console.info(whence, "corpus", sought)
        Console.info(whence, "matcher", matcher)
        Console.info(whence, "showMemory", showMemory)
        Console.info(whence, "typewriterFactory", typewriterFactory)
    }

    fun makeMonkeys(): Monkeys {
        // I don't make monkeys; I just train them!
        val typewriter = typewriterFactory.typewriter()
        println("typewriter = $typewriter")
        val monkeyList = (0 until numMonkeys).map { Monkey(it, typewriter) }
        println("matcher = $matcher")
        return Monkeys(monkeyList, sought, matcher)
    }
}