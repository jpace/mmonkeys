package org.incava.mmonkeys.mky.corpus.sc

import org.incava.mmonkeys.type.Keys

class SequenceStrategy(private val validSequences: Map<Char, List<Char>>, val charProvider: () -> Char) {
    fun typeWord(): String {
        val builder = StringBuilder()
        var prev: Char? = null
        while (true) {
            if (prev == null) {
                val ch = charProvider()
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