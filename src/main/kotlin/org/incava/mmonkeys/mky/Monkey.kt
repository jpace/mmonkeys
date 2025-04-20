package org.incava.mmonkeys.mky

import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.words.Words

abstract class Monkey(val id: Int, val typewriter: Typewriter, var manager: Manager? = null) {
    override fun toString(): String = "Monkey(id=$id)"

    abstract fun findMatches(): Words

    fun recordWords(words: Words) {
        manager?.update(this, words)
    }
}