package org.incava.mmonkeys.mky.corpus.sc.map

import org.incava.mmonkeys.mky.corpus.sc.SingleCorpusMonkey
import org.incava.mmonkeys.mky.corpus.sc.StrategyFactory
import org.incava.mmonkeys.mky.mind.TypeStrategy
import org.incava.mmonkeys.words.Words

class MapGenMonkey(id: Int, override val corpus: MapCorpus) : SingleCorpusMonkey(id, corpus) {
    override val strategy = StrategyFactory.fullRandom()

    override fun findMatches(): Words {
        val word = typeWord()
        // keystrokes here are only through the word, not the trailing space
        val forLength = corpus.forLength(word.length)
        val indices = forLength?.get(word)
        return if (indices == null) {
            toNonMatch(word)
        } else {
            // we're always removing/matching the *first* index
            val index = indices.first()
            toWordsMatch(word, index)
        }
    }
}
