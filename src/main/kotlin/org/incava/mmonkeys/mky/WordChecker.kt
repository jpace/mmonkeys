package org.incava.mmonkeys.mky

import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.words.Attempt
import org.incava.mmonkeys.words.AttemptFactory
import org.incava.mmonkeys.words.Word

class WordChecker(val corpus: Corpus, private val observer: AttemptObserver) {
    fun processAttempt(str: String): Attempt {
        val index = corpus.findMatch(str)
        return if (index == null) {
            AttemptFactory.failed(str).also { observer.onFailed(str) }
        } else {
            corpus.setMatched(index)
            val word = Word(str, index)
            AttemptFactory.succeeded(word).also { observer.onSuccess(it) }
        }
    }
}
