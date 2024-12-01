package org.incava.mmonkeys.mky

import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.words.Words

abstract class Monkey(val id: Int, open val corpus: Corpus) {
    val monitors = mutableListOf<MonkeyMonitor>()
    val matchesByLength = mutableMapOf<Int, Int>()

    override fun toString(): String = "Monkey(id=$id)"

    abstract fun findMatches(): Words

    fun notifyMonitors(words: Words) {
        monitors.forEach {
            it.notify(this, words)
        }
    }
}