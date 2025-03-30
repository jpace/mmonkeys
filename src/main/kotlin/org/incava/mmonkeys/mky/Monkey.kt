package org.incava.mmonkeys.mky

import org.incava.mmonkeys.words.Words

abstract class Monkey(val id: Int) {
    var manager: MonkeyMonitor? = null

    override fun toString(): String = "Monkey(id=$id)"

    abstract fun findMatches(): Words

    fun recordWords(words: Words) {
        manager?.update(this, words)
    }
}