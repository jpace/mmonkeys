package org.incava.mmonkeys.mky.corpus.sc.map

import org.incava.mmonkeys.mky.WordChecker
import org.incava.mmonkeys.words.Attempt
import org.incava.mmonkeys.words.AttemptFactory
import org.incava.mmonkeys.words.Word

class MapWordChecker(override val corpus: MapCorpus) : WordChecker(corpus) {
    override fun processAttempt(str: String): Attempt {
        val indices = corpus.forLength(str.length)?.get(str)
        return if (indices == null) {
            AttemptFactory.failed(str)
        } else {
            val index = corpus.setMatched(str)
            val word = Word(str, index)
            AttemptFactory.succeeded(word)
        }
    }
}
