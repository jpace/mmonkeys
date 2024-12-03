package org.incava.mmonkeys.mky.corpus

import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter

class MapGenMonkey(id: Int, typewriter: Typewriter, override val corpus: MapCorpus) : SingleCorpusMonkey(id, typewriter, corpus) {
    override fun check(): MatchData {
        val builder = StringBuilder()
        while (true) {
            val ch = typewriter.nextCharacter()
            if (ch == Keys.END_CHAR) {
                break
            } else {
                builder.append(ch)
            }
        }
        val word = builder.toString()
        val numChars = word.length
        val forLength = corpus.forLength(numChars) ?: return noMatch(numChars)
        val indices = forLength[word] ?: return noMatch(numChars)
        // we're always removing/matching the *first* index
        val index = indices.first()
        corpus.setMatched(index)
        return match(numChars, index)
    }
}