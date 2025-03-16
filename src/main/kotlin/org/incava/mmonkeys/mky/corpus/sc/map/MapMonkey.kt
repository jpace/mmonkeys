package org.incava.mmonkeys.mky.corpus.sc.map

import org.incava.mmonkeys.mky.corpus.sc.RandomStrategy
import org.incava.mmonkeys.mky.corpus.sc.SingleCorpusMonkey
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.words.Words

class MapMonkey(id: Int, typewriter: Typewriter, override val corpus: MapCorpus) : SingleCorpusMonkey(id, typewriter, corpus) {
    private val strategy = RandomStrategy(typewriter.chars)

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
