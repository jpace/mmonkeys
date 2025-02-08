package org.incava.mmonkeys.mky.corpus.sc

import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter

class WeightedMonkey(id: Int, typewriter: Typewriter, corpus: Corpus) : DefaultMonkey(id, typewriter, corpus) {
    private val strategy = WeightFilter(corpus.words)

    override fun typeWord(): String {
        val builder = StringBuilder()
        while (true) {
            val ch = strategy.nextCharacter()
            if (ch == Keys.END_CHAR) {
                return builder.toString()
            } else {
                builder.append(ch)
            }
        }
    }
}