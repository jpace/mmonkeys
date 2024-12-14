package org.incava.mmonkeys.mky.corpus

import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.words.Words

class MapMonkey(id: Int, typewriter: Typewriter, override val corpus: MapCorpus) :
    SingleCorpusMonkey(id, typewriter, corpus) {
    override fun findMatches(): Words {
        val toEndOfWord = randomLength()
        val numChars = toEndOfWord - 1
        val forLength = corpus.forLength(numChars) ?: return toNonMatch(toEndOfWord)
        val word = (0 until numChars).fold("") { str, _ ->
            str + typewriter.nextWordCharacter()
        }
        val indices = forLength[word]
        return if (indices == null) {
            toNonMatch(toEndOfWord)
        } else {
            // we're always removing/matching the *first* index
            val index = indices.first()
            corpus.setMatched(word, numChars)
            toWordsMatch(word, index, toEndOfWord)
        }
    }
}
