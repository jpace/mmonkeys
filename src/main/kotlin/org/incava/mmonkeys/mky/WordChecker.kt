package org.incava.mmonkeys.mky

import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.words.Attempt
import org.incava.mmonkeys.words.AttemptFactory
import org.incava.mmonkeys.words.Word

open class WordChecker(open val corpus: Corpus, private val corpusUpdater: CorpusUpdater = CorpusUpdater(corpus)) {
    open fun processAttempt(str: String): Attempt {
        val match = corpus.findMatch(str)
        return if (match == null) {
            AttemptFactory.failed(str)
        } else {
            corpusUpdater.indexMatched(match)
            val word = Word(str, match)
            AttemptFactory.succeeded(word)
        }
    }
}
