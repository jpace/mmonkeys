package org.incava.mmonkeys.mky.number

import org.incava.mmonkeys.mky.corpus.dc.AttemptedTypewriter

class NumbersMonkeyManager(val corpus: NumberedCorpus) {
    var id = 0

    fun createMonkey() : NumbersMonkey {
        val checker = NumbersChecker(corpus)
        val typewriter = AttemptedTypewriter()
        return NumbersMonkey(id++, checker, typewriter)
    }
}