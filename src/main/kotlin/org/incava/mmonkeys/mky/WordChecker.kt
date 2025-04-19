package org.incava.mmonkeys.mky

import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.sc.MatchResults
import org.incava.mmonkeys.words.Words
import org.incava.mmonkeys.words.WordsFactory

open class WordChecker(open val corpus: Corpus) {
    open fun processWord(word: String): Words {
        val match = corpus.findMatch(word)
        return if (match == null) {
            WordsFactory.toWordsNonMatch(word)
        } else {
            MatchResults.toWordsMatch(corpus, word, match)
        }
    }
}
