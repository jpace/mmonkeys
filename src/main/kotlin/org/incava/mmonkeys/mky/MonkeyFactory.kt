package org.incava.mmonkeys.mky

import org.incava.mmonkeys.corpus.WordCorpus
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.mky.mind.TypeStrategy
import org.incava.mmonkeys.type.Typewriter

class MonkeyFactory(val manager: Manager, val corpus: WordCorpus) {
    var id = 1
    private val checker = WordChecker(corpus, manager)

    fun createMonkey(strategy: TypeStrategy): Monkey {
        val typewriter = Typewriter(checker)
        return Monkey(id++, strategy, typewriter)
    }
}