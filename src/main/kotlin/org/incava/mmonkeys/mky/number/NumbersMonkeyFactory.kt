package org.incava.mmonkeys.mky.number

import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.mky.corpus.dc.AttemptedTypewriter

class NumbersMonkeyFactory(val manager: Manager, val corpus: NumberedCorpus) {
    var id = 0

    fun createMonkey(): NumbersMonkey {
        val checker = NumbersChecker(corpus)
        val typewriter = AttemptedTypewriter(manager)
        return NumbersMonkey(id++, checker, typewriter)
    }
}