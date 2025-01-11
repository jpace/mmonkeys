package org.incava.mmonkeys.mky.corpus.sc

import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter

class WeightedMonkey(id: Int, typewriter: WeightedTypewriter, corpus: Corpus) : DefaultMonkey(id, typewriter, corpus) {
    val filter = WeightFilter(corpus.words)

    override fun typeWord(): String {
        val builder = StringBuilder()
        while (true) {
            val ch = filter.nextCharacter()
            if (ch == Keys.END_CHAR) {
                return builder.toString()
            } else {
                builder.append(ch)
            }
        }
    }
}