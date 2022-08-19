package org.incava.mmonkeys.exec

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.util.Console.log

data class SimulationParams(
    val charList: List<Char> = ('a'..'z').toList() + ' ',
    val numMonkeys: Int = charList.size,
    val sought: String,
    val matching: ((monkey: Monkey, str: String) -> Matcher),
    val maxAttempts: Long = 100_000_000L,
) {
    fun summarize() {
        log("char.last", charList.last())
        log("# monkeys", numMonkeys)
        log("sought", sought)
        log("matching", matching)
    }
}