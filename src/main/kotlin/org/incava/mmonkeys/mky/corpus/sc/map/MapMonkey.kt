package org.incava.mmonkeys.mky.corpus.sc.map

import org.incava.mmonkeys.mky.corpus.sc.SingleCorpusMonkey
import org.incava.mmonkeys.mky.corpus.sc.StrategyFactory
import org.incava.mmonkeys.words.Words

class MapMonkey(id: Int, override val corpus: MapCorpus) : SingleCorpusMonkey(id, corpus) {
    override val strategy = StrategyFactory.fullRandom()

    override fun findMatches(): Words {
        val word = typeWord()
        val forLength = corpus.forLength(word.length) ?: return toNonMatch(word)
        val indices = forLength[word]
        return if (indices == null) {
            toNonMatch(word)
        } else {
            // we're always removing/matching the *first* index
            val index = indices.removeAt(0)
            if (indices.isEmpty()) {
                forLength.remove(word)
            }
            // corpus.setMatched(word, numChars)
            toWordsMatch(word, index)
        }
    }
}
