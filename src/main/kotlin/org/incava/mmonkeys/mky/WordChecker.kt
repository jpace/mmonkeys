package org.incava.mmonkeys.mky

import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.words.Attempt
import org.incava.mmonkeys.words.AttemptFactory
import org.incava.mmonkeys.words.Word

open class WordChecker(open val corpus: Corpus) {
    open fun processAttempt(str: String): Attempt {
        val match = corpus.findMatch(str)
        return if (match == null) {
            AttemptFactory.failed(str)
        } else {
            corpus.setMatched(match)
            val word = Word(str, match)
            AttemptFactory.succeeded(word)
        }
    }
}
