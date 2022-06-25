package org.incava.mmonkeys.exec

data class SimulationParams(
    val charList: List<Char> = ('a'..'z').toList() + ' ',
    val numMonkeys: Int = charList.size,
    val sought: String,
    val maxAttempts: Long = 100_000_000L,
    val iterations: Int = 10,
)