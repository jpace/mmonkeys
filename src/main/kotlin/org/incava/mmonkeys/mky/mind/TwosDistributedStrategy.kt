package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.rand.CharSlotsFactory
import org.incava.mmonkeys.rand.CharsDist2Profile
import org.incava.mmonkeys.rand.Sequences
import org.incava.mmonkeys.rand.SequencesFactory

class TwosDistributedStrategy(sequences: Sequences) : TwosStrategy(sequences) {
    constructor(words: List<String>) : this(SequencesFactory.createFromWords(words))

    private val profile: CharsDist2Profile

    init {
        val firsts = CharSlotsFactory.createFirsts(sequences.twos)
        val seconds = CharSlotsFactory.createSeconds(sequences.twos)
        profile = CharsDist2Profile(firsts, seconds)
    }

    override fun getChar(firstChar: Char): Char = profile.getChar(firstChar)
    override fun getChar(): Char = profile.getChar()
}