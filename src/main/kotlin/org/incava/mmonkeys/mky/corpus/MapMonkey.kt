package org.incava.mmonkeys.mky.corpus

import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.type.Typewriter

class MapMonkey(id: Int, typewriter: Typewriter, override val corpus: MapCorpus) : Monkey(id, typewriter, corpus) {
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
                corpus.matched(word, length)
                return match(length, index)
            }
        }
        return noMatch(length)
    }

    fun nextChars(length: Int): String {
        // returns a string of the given length
        return (0 until length).fold(StringBuilder()) { sb, _ ->
            val ch = typewriter.nextWordCharacter()
            sb.append(ch)
        }.toString()
    }
}
