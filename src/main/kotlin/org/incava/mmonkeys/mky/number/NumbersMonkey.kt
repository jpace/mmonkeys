package org.incava.mmonkeys.mky.number

import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.type.Typewriter
import kotlin.random.Random

class NumbersMonkey(id: Int, typewriter: Typewriter, override val corpus: NumberedCorpus) : Monkey(id, typewriter, corpus) {
    private fun findMatch(numChars: Int, forLength: Map<Long, List<Int>>): MatchData {
        // if null, we must be called with the wrong (> 13) length:
        val encoded = RandEncoded.random(numChars)
        val matchEncoded = forLength[encoded] ?: return noMatch(numChars)
        return if (matchEncoded.isNotEmpty()) {
            val word = StringEncoder.decode(encoded)
            val index = corpus.matched(encoded, numChars)
            match(numChars, index)
        } else {
            noMatch(numChars)
        }
    }

    override fun check(): MatchData {
        val length = randomLength()
        val soughtLen = length - 1
        val forLength = corpus.longsForLength(soughtLen) ?: return noMatch(soughtLen)
        return findMatch(soughtLen, forLength)
    }
}