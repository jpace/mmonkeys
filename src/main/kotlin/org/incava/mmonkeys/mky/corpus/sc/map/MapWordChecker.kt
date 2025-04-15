package org.incava.mmonkeys.mky.corpus.sc.map

import org.incava.mmonkeys.mky.WordChecker
import org.incava.mmonkeys.mky.corpus.sc.MatchResults
import org.incava.mmonkeys.words.Words
import org.incava.mmonkeys.words.WordsFactory

class MapWordChecker(override val corpus: MapCorpus) : WordChecker(corpus) {
    override fun processWord(word: String): Words {
        val forLength = corpus.forLength(word.length) ?: return WordsFactory.toWordsNonMatch(word)
        val indices = forLength[word]
        return if (indices == null) {
            WordsFactory.toWordsNonMatch(word)
        } else {
            val index = corpus.setMatched(word, word.length)
            MatchResults.toWordsMatch(corpus, word, index)
        }
    }
}
