package org.incava.mmonkeys.mky

import org.incava.mmonkeys.words.Words

abstract class Monkey(val id: Int) {
    abstract fun findMatches(): Words
}