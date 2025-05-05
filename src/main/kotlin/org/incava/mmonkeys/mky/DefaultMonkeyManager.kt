package org.incava.mmonkeys.mky

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.mky.mind.RandomStrategy
import org.incava.mmonkeys.mky.mind.TypeStrategy
import org.incava.mmonkeys.type.DefaultTypewriter
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.words.Attempt

class DefaultMonkeyManager(val corpus: Corpus) {
    var id = 1
    val observer = object : AttemptObserver {
        override fun onSuccess(attempt: Attempt) {
            Qlog.info("attempt", attempt)
        }

        override fun onFailed(string: String) {
            // Qlog.info("string", string)
        }
    }
    val checker = WordChecker(corpus, observer)

    fun createMonkeyRandom(): DefaultMonkey {
        val strategy = RandomStrategy(Keys.fullList())
        val typewriter = DefaultTypewriter(checker)
        return DefaultMonkey(id++, strategy, typewriter)
    }

    fun createMonkey(strategy: TypeStrategy): DefaultMonkey {
        val typewriter = DefaultTypewriter(checker)
        return DefaultMonkey(id++, strategy, typewriter)
    }
}