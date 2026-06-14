package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.chars.Chars2
import org.incava.mmonkeys.rand.CharsRandomProfile
import org.incava.mmonkeys.rand.Sequences
import org.incava.mmonkeys.rand.SequencesFactory

class ThreesRandomStrategy(sequences: Sequences) : ThreesStrategy(sequences) {
    constructor(words: List<String>) : this(SequencesFactory.createFromWords(words))

    private val random = CharsRandomProfile(profile.firsts, profile.seconds, profile.thirds)

    override fun getChar(chars: Chars2): Char {
        return random.getChar(chars)
    }

    override fun getChar(firstChar: Char): Char {
        return random.getChar(firstChar)
    }

    override fun getChar(): Char {
        return random.getChar()
    }
}