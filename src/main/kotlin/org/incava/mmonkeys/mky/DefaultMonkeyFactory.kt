package org.incava.mmonkeys.mky

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.corpus.WordCorpus
import org.incava.mmonkeys.mky.mind.RandomStrategy
import org.incava.mmonkeys.mky.mind.TypeStrategy
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.words.Attempt

class DefaultMonkeyFactory(val manager: MonkeyMonitor, val corpus: WordCorpus) {
    var id = 1
    private val observer = object : AttemptObserver<Monkey> {
        override fun onSuccess(observed: Monkey, attempt: Attempt) {
            Qlog.info("observed", observed)
            Qlog.info("attempt", attempt)
            manager.update(observed, attempt)
        }

        override fun onFailed(observed: Monkey, attempt: Attempt) {
            Qlog.info("observed", observed)
            Qlog.info("attempt", attempt)
            manager.update(observed, attempt)
        }
    }
    private val checker = WordChecker(corpus, observer)

    fun createMonkey(strategy: TypeStrategy): DefaultMonkey {
        val typewriter = Typewriter(checker)
        return DefaultMonkey(id++, strategy, typewriter)
    }
}