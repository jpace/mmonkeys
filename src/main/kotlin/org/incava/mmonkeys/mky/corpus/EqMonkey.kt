package org.incava.mmonkeys.mky.corpus

import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter

class EqMonkey(id: Int, typewriter: Typewriter, corpus: Corpus) : SingleCorpusMonkey(id, typewriter, corpus) {
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
        val index = corpus.words.indices.find { index ->
            // not sure which condition is faster:
            corpus.words[index] == word && !corpus.isMatched(index)
        }
        return if (index == null) {
            noMatch(word.length)
        } else {
            corpus.setMatched(index)
            match(word.length, index)
        }
    }
}