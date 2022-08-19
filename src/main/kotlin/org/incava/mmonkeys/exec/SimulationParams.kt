package org.incava.mmonkeys.exec

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.util.Console

data class SimulationParams(
    val charList: List<Char> = ('a'..'z').toList() + ' ',
    val numMonkeys: Int = charList.size,
    val sought: String,
    val matching: ((monkey: Monkey, str: String) -> Matcher),
    val maxAttempts: Long = 100_000_000L,
) {
    fun summarize() {
        val whence = "SimulationParams"
        Console.info(whence, "char.last", charList.last())
        Console.info(whence, "# monkeys", numMonkeys)
        Console.info(whence, "sought", sought)
        Console.info(whence, "matching", matching)
    }
}