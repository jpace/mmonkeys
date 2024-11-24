package org.incava.mmonkeys.mky.corpus

import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.type.Typewriter

class MapMonkey(id: Int, typewriter: Typewriter, override val corpus: MapCorpus) : Monkey(id, typewriter, corpus) {
    override fun check(): MatchData {
        val toEndOfWord = randomLength()
        val numChars = toEndOfWord - 1
        val forLength = corpus.forLength(numChars) ?: return noMatch(numChars)
        val word = (0 until numChars).fold("") { str, _ ->
            str + typewriter.nextWordCharacter()
        }
        val indices = forLength[word] ?: return noMatch(numChars)
        // we're always removing/matching the *first* index
        val index = indices.first()
        corpus.setMatched(word, numChars)
        return match(numChars, index)
   }
}
