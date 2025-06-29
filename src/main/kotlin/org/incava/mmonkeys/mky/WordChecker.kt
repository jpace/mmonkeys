package org.incava.mmonkeys.mky

import org.incava.mmonkeys.corpus.WordCorpus
import org.incava.mmonkeys.words.Attempt
import org.incava.mmonkeys.words.AttemptFactory
import org.incava.mmonkeys.words.Word

class WordChecker(val corpus: WordCorpus, private val observer: AttemptObserver<Monkey>) {
    fun check(monkey: Monkey, str: String): Attempt {
        val index = corpus.findMatch(str)
        return if (index == null) {
            AttemptFactory.failed(str).also { observer.onFailed(monkey, it) }
        } else {
            corpus.setMatched(index, str)
            val word = Word(str, index)
            AttemptFactory.succeeded(word).also { observer.onSuccess(monkey, it) }
        }
    }
}
