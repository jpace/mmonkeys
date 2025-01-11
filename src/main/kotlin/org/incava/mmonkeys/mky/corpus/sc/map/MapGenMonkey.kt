package org.incava.mmonkeys.mky.corpus.sc.map

import org.incava.mmonkeys.mky.corpus.sc.SingleCorpusMonkey
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.words.Words

class MapGenMonkey(id: Int, typewriter: Typewriter, override val corpus: MapCorpus) :
    SingleCorpusMonkey(id, typewriter, corpus) {
    override fun findMatches(): Words {
        val builder = StringBuilder()
        while (true) {
            val ch = typewriter.nextCharacter()
            if (ch == Keys.END_CHAR) {
                break
            } else {
                builder.append(ch)
            }
        }
        val typed = builder.toString()
        val numChars = typed.length
        // keystrokes here are only through the word, not the trailing space
        val attemptKeystrokes = numChars + 1
        val forLength = corpus.forLength(numChars)
        val indices = forLength?.get(typed)
        return if (indices != null) {
            // we're always removing/matching the *first* index
            val index = indices.first()
            toWordsMatch(typed, index, attemptKeystrokes)
        } else {
            toNonMatch(attemptKeystrokes)
        }
    }
}
