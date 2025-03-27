package org.incava.mmonkeys.mky.corpus.sc.map

import org.incava.mmonkeys.mky.corpus.sc.CorpusMonkey
import org.incava.mmonkeys.mky.corpus.sc.MatchResults
import org.incava.mmonkeys.mky.mind.RandomStrategy
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.words.Words

class MapGenMonkey(id: Int, override val corpus: MapCorpus) : CorpusMonkey(id, corpus, RandomStrategy(Keys.fullList())) {
    override fun findMatches(): Words {
        val word = typeWord()
        // keystrokes here are only through the word, not the trailing space
        val forLength = corpus.forLength(word.length)
        val indices = forLength?.get(word)
        return if (indices == null) {
            MatchResults.toNonMatch(word)
        } else {
            // we're always removing/matching the *first* index
            val index = indices.first()
            MatchResults.toWordsMatch(corpus, word, index)
        }
    }
}
