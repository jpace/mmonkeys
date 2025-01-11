package org.incava.mmonkeys.mky.corpus.sc

import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.words.Words

open class DefaultMonkey(id: Int, typewriter: Typewriter, corpus: Corpus) : SingleCorpusMonkey(id, typewriter, corpus) {
    // this is actually random.
    open fun typeWord(): String {
        val builder = StringBuilder()
        while (true) {
            val ch = typewriter.nextCharacter()
            if (ch == Keys.END_CHAR) {
                return builder.toString()
            } else {
                builder.append(ch)
            }
        }
    }

    override fun findMatches(): Words {
        val typed = typeWord()
        val match = corpus.words.withIndex().find { (index, word) ->
            // not sure which condition is faster:
            corpus.words[index] == typed && !corpus.isMatched(index)
        } ?: return toNonMatch(typed.length + 1)
        // keystrokes here are only through the word, not the trailing space
        return toWordsMatch(match.value, match.index, typed.length + 1)
    }
}