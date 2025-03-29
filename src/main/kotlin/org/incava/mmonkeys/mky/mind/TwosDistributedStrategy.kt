package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.mky.corpus.sc.Sequences
import org.incava.mmonkeys.rand.DistributedRandom

class TwosDistributedStrategy(sequences: Sequences) : TwosStrategy(sequences) {
    constructor(words: List<String>) : this(Sequences(words))

    private val firsts: DistributedRandom<Char, Int>
    private val seconds: Map<Char, DistributedRandom<Char, Int>>

    init {
        firsts = DistributedStrategy.createFirsts2(sequences.twos)
        seconds = DistributedStrategy.createSeconds2(sequences.twos)
    }

    override fun getChar(firstChar: Char): Char = DistributedStrategy.getChar(seconds, firstChar)
    override fun getChar(): Char = DistributedStrategy.getChar(firsts)
}