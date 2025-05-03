package org.incava.mmonkeys.mky.number

import org.incava.mmonkeys.type.Typewriter

object NumbersMonkeyFactory {
    fun createMonkey(id: Int, corpus: NumberedCorpus, typewriter: Typewriter) : NumbersMonkey {
        val updater = NumberedCorpusUpdater(corpus)
        val checker = NumbersChecker(corpus, updater)
        return NumbersMonkey(id, checker, typewriter)
    }
}