package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.rand.CharsDist3Profile
import org.incava.mmonkeys.rand.Sequences
import org.incava.mmonkeys.rand.SequencesFactory

class ThreesDistributedStrategy(sequences: Sequences) : ThreesStrategy(sequences) {
    constructor(words: List<String>) : this(SequencesFactory.createFromWords(words))

    private val dist = CharsDist3Profile(profile.firsts, profile.seconds, profile.thirds)

    override fun getChar(firstChar: Char, secondChar: Char): Char {
        return dist.getChar(firstChar, secondChar)
    }

    override fun getChar(firstChar: Char): Char = dist.getChar(firstChar)

    override fun getChar(): Char = dist.getChar()
}