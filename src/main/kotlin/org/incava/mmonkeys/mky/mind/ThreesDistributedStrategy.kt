package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.mky.corpus.sc.Sequences
import org.incava.mmonkeys.rand.DistributedRandom

class ThreesDistributedStrategy(sequences: Sequences) : ThreesStrategy(sequences) {
    constructor(words: List<String>) : this(Sequences(words))

    private val firsts: DistributedRandom<Char, Int>
    private val seconds: Map<Char, DistributedRandom<Char, Int>>
    private val thirds: Map<Char, Map<Char, DistributedRandom<Char, Int>>>

    init {
        val threes = sequences.threes
        firsts = threes.mapValues { (_, second) ->
            second.values.sumOf { thirds ->
                DistributedStrategy.sumOfValues(thirds)
            }
        }.let { DistributedRandom(it) }
        seconds = threes.mapValues { (_, second) ->
            second.mapValues { (_, thirds) ->
                DistributedStrategy.sumOfValues(thirds)
            }.let { DistributedRandom(it) }
        }
        thirds = threes.mapValues { (_, second) ->
            second.mapValues { (_, thirds) ->
                DistributedRandom(thirds)
            }
        }
    }

    override fun getChar(firstChar: Char, secondChar: Char): Char {
        return thirds.getValue(firstChar).getValue(secondChar).nextRandom()
    }

    override fun getChar(firstChar: Char): Char = DistributedStrategy.getChar(seconds, firstChar)
    override fun getFirstChar(): Char = DistributedStrategy.getFirstChar(firsts)
}