package org.incava.mmonkeys.mky.corpus.sc.map

import org.incava.mmonkeys.type.Keys

class SequenceWord(initStrategy: () -> Char, nextStrategy: (Char) -> Char) {
    val result: String

    init {
        val builder = StringBuilder()
        while (true) {
            val ch = if (builder.isEmpty()) {
                initStrategy()
            } else {
                nextStrategy(builder.last())
            }
            if (ch == Keys.END_CHAR) {
                break
            } else {
                builder.append(ch)
            }
        }
        result = builder.toString()
    }
}