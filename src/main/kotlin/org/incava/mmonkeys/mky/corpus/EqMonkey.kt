package org.incava.mmonkeys.mky.corpus

import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.words.Words

class EqMonkey(id: Int, typewriter: Typewriter, corpus: Corpus) : SingleCorpusMonkey(id, typewriter, corpus) {
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
        val match = corpus.words.withIndex().find { (index, word) ->
            // not sure which condition is faster:
            corpus.words[index] == typed && !corpus.isMatched(index)
        } ?: return toNonMatch(typed.length + 1)

        // keystrokes here are only through the word, not the trailing space
        return toWordsMatch(match.value, match.index, typed.length + 1)
    }
}