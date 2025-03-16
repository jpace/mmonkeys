package org.incava.mmonkeys.mky.corpus.sc

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.mky.corpus.type.TypeStrategy
import org.incava.mmonkeys.type.Keys

class SequenceStrategy(private val sequences: Sequences, val initStrategy: () -> Char) : TypeStrategy() {
    override fun typeCharacter(): Char {
        TODO("Not yet implemented")
    }

    override fun typeWord(): String {
        val builder = StringBuilder()
        var ch = initStrategy()
        while (ch != Keys.END_CHAR) {
            builder.append(ch)
            ch = getNext(ch)
        }
        return builder.toString()
    }

    fun getNext(ch: Char): Char {
        val possible = sequences.presentTwos.getValue(ch)
        Qlog.info("possible[$ch]", possible.toSortedSet().joinToString(" "))
        return possible.random()
    }
}