package org.incava.mmonkeys.mky

import org.incava.mmonkeys.words.Words

abstract class Monkey(val id: Int) {
    val monitors = mutableListOf<MonkeyMonitor>()
    val matchesByLength = mutableMapOf<Int, Int>()
    var totalKeystrokes = 0L

    override fun toString(): String = "Monkey(id=$id)"

    abstract fun findMatches(): Words

    fun recordWords(words: Words) {
        words.words.forEach { matchesByLength.merge(it.string.length, 1) { prev, _ -> prev + 1 } }
        monitors.forEach {
            it.update(this, words)
        }
        totalKeystrokes += words.totalKeyStrokes
    }
}