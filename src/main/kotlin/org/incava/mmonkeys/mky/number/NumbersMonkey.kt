package org.incava.mmonkeys.mky.number

import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.type.Typewriter

class NumbersMonkey(id: Int, typewriter: Typewriter, override val corpus: NumberedCorpus) : Monkey(id, typewriter, corpus) {
    override fun check(): MatchData {
        val length = randomLength()
        val numChars = length - 1
        val forLength = corpus.longsForLength(numChars) ?: return noMatch(numChars)
        // if null, we must be called with the wrong (> 13) length:
        val encoded = RandEncoded.random(numChars)
        val matchEncoded = forLength[encoded] ?: return noMatch(numChars)
        return if (matchEncoded.isNotEmpty()) {
            val index = corpus.setMatched(encoded, numChars)
            match(numChars, index)
        } else {
            noMatch(numChars)
        }
    }
}