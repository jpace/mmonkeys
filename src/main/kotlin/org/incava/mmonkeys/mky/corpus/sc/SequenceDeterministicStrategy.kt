package org.incava.mmonkeys.mky.corpus.sc

import org.incava.mmonkeys.mky.corpus.type.TypeStrategy
import org.incava.mmonkeys.rand.DistributedRandom
import org.incava.mmonkeys.type.Keys

class SequenceDeterministicStrategy(sequences: Sequences, val initStrategy: () -> Char) : TypeStrategy() {
    private val randomByChar : Map<Char, DistributedRandom<Char, Int>>

    init {
        val twos = sequences.presentTwosCounted
        randomByChar = twos.mapValues { (first, second) ->
            DistributedRandom(second)
        }
    }

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

    open fun getNext(ch: Char): Char {
        return randomByChar.getValue(ch).nextRandom()
    }
}