package org.incava.mmonkeys.mky.corpus.sc.map

import org.incava.mmonkeys.mky.corpus.sc.ContextStrategy
import org.incava.mmonkeys.mky.corpus.sc.SingleCorpusMonkey
import org.incava.mmonkeys.mky.corpus.sc.StrategyFactory
import org.incava.mmonkeys.mky.mind.Context
import org.incava.mmonkeys.words.Words

class MapMonkey(id: Int, override val corpus: MapCorpus) : SingleCorpusMonkey(id, corpus) {
    private val random1 = StrategyFactory.random()
    private val strategy = ContextStrategy(random1::invoke) { random1() }

    override fun findMatches(): Words {
        val word = strategy.typeWord()
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
