package org.incava.mmonkeys.mky

import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.sc.MatchResults
import org.incava.mmonkeys.words.Words

open class WordChecker(open val corpus: Corpus) {
    open fun processWord(word: String): Words {
        val match = corpus.findMatch(word)
        return if (match == null) {
            MatchResults.toNonMatch(word)
        } else {
            // keystrokes here are only through the word, not the trailing space
            MatchResults.toWordsMatch(corpus, word, match)
        }
    }
}
