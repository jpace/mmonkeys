package org.incava.mmonkeys.exec

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.Matcher

data class SimulationParams(
    val charList: List<Char> = ('a'..'z').toList() + ' ',
    val numMonkeys: Int = charList.size,
    val sought: String,
    val maxAttempts: Long = 100_000_000L,
    val iterations: Int = 1,
    val matching: ((monkey: Monkey, str: String) -> Matcher),
)