package org.incava.mmonkeys.mky

import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.words.Words
import org.incava.mmonkeys.words.WordsFactory

open class WordChecker(open val corpus: Corpus, private val corpusUpdater: CorpusUpdater = CorpusUpdater(corpus)) {
    open fun processWord(word: String): Words {
        val match = corpus.findMatch(word)
        return if (match == null) {
            WordsFactory.toWordsNonMatch(word)
        } else {
            corpusUpdater.indexMatched(match)
            WordsFactory.toWordsMatch(word, match)
        }
    }
}
