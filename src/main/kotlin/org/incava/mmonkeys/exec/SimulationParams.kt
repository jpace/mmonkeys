package org.incava.mmonkeys.exec

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.Corpus
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.match.StringMatcher
import org.incava.mmonkeys.type.StandardTypewriter
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.util.Console
import kotlin.reflect.KFunction1

typealias TypewriterCtor = (List<Char>) -> Typewriter
typealias MatcherCtor = (Monkey, Corpus) -> Matcher

data class SimulationParams(
    val charList: List<Char> = ('a'..'z').toList() + ' ',
    val numMonkeys: Int = charList.size,
    val sought: Corpus,
    val matching: MatcherCtor,
    val maxAttempts: Long = 100_000_000L,
    val typewriterType: TypewriterCtor = ::StandardTypewriter,
    val showMemory: Boolean = false,
) {
    fun summarize() {
        val whence = "SimulationParams"
        Console.info(whence, "# chars", charList.size)
        Console.info(whence, "# monkeys", numMonkeys)
        Console.info(whence, "sought", sought)
//        Console.info(whence, "matching", matching)
        Console.info(whence, "showMemory", showMemory)
    }
}