package org.incava.mmonkeys.mky.corpus

import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.type.Typewriter

class MapCorpusMonkey(override val corpus: MapCorpus, id: Int, typewriter: Typewriter) : CorpusMonkey(corpus, id, typewriter) {
    override fun check(): MatchData {
        val toEndOfWord = randomLength()
        val length = toEndOfWord - 1
        val forLength = corpus.lengthToStringsToIndices[length]
        if (forLength != null) {
            val word = nextChars(length)
            val indices = forLength[word]
            if (indices != null) {
                // we're always removing/matching the *first* index
                val index = indices.first()
                corpus.matched(word)
                return match(length, index)
            }
        }
        return noMatch(length)
    }
}
