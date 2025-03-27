package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.mky.corpus.sc.Sequences
import org.incava.mmonkeys.rand.DistributedRandom

class TwosDistributedStrategy(sequences: Sequences) : TwosStrategy(sequences) {
    constructor(words: List<String>) : this(Sequences(words))

    private val firsts: DistributedRandom<Char, Int>
    private val seconds: Map<Char, DistributedRandom<Char, Int>>

    init {
        val twos = sequences.twos
        firsts = twos.mapValues { (_, second) ->
            DistributedStrategy.sumOfValues(second)
        }.let { DistributedRandom(it) }
        seconds = twos.mapValues { (_, second) ->
            DistributedRandom(second)
        }
    }

    override fun getChar(firstChar: Char): Char = DistributedStrategy.getChar(seconds, firstChar)
    override fun getFirstChar(): Char = DistributedStrategy.getFirstChar(firsts)
}