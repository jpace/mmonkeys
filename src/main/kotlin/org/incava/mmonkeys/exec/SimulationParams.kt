package org.incava.mmonkeys.exec

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.type.StandardTypewriter
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.util.Console
import kotlin.reflect.KFunction1

data class SimulationParams(
    val charList: List<Char> = ('a'..'z').toList() + ' ',
    val numMonkeys: Int = charList.size,
    val sought: String,
    val matching: ((monkey: Monkey, str: String) -> Matcher),
    val maxAttempts: Long = 100_000_000L,
    val typewriterType: KFunction1<List<Char>, Typewriter> = ::StandardTypewriter,
) {
    fun summarize() {
        val whence = "SimulationParams"
        Console.info(whence, "# chars", charList.size)
        Console.info(whence, "# monkeys", numMonkeys)
        Console.info(whence, "sought", sought)
        Console.info(whence, "matching", matching)
    }
}