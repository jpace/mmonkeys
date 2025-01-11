package org.incava.mmonkeys.mky.corpus.sc

import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter

class SequenceMonkey(id: Int, typewriter: Typewriter, corpus: Corpus) : DefaultMonkey(id, typewriter, corpus) {
    val validSequences: Map<Char, List<Char>> = SequenceFilter(corpus.words).presentTwos

    override fun typeWord(): String {
        val builder = StringBuilder()
        var prev: Char? = null
        while (true) {
            if (prev == null) {
                val ch = typewriter.nextCharacter()
                if (ch == Keys.END_CHAR) {
                    return builder.toString()
                } else {
                    builder.append(ch)
                    prev = ch
                }
            } else {
                // Qlog.info("prev", prev)
                val possible = validSequences.getValue(prev)
                // Qlog.info("possible", possible)
                val ch = possible.random()
                // Qlog.info("ch", ch)
                if (ch == Keys.END_CHAR) {
                    return builder.toString()
                } else {
                    builder.append(ch)
                    prev = ch
                }
            }
        }
    }
}