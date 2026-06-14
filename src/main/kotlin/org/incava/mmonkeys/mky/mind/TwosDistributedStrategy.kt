package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.chars.CharsElementFactory
import org.incava.mmonkeys.rand.CharSlotsFactory
import org.incava.mmonkeys.rand.CharsDist2Profile
import org.incava.mmonkeys.rand.Sequences
import org.incava.mmonkeys.rand.SequencesFactory

class TwosDistributedStrategy(sequences: Sequences) : TwosStrategy(sequences) {
    constructor(words: List<String>) : this(SequencesFactory.createFromWords(words))

    private val profile: CharsDist2Profile

    init {
        val asCharToLists = CharsElementFactory.toMapToList(sequences.twos)
        val firsts = CharSlotsFactory.createSlots(asCharToLists)
        val seconds = CharSlotsFactory.createMapToSlots(sequences.twos)
        profile = CharsDist2Profile(firsts, seconds)
    }

    override fun getChar(firstChar: Char): Char = profile.getChar(firstChar)
    override fun getChar(): Char = profile.getChar()
}