package org.incava.mmonkeys.mky.number

import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.type.Typewriter
import kotlin.random.Random

class NumbersMonkey(id: Int, typewriter: Typewriter, override val corpus: NumberedCorpus) : Monkey(id, typewriter, corpus) {
    private fun findMatch(soughtLen: Int, forLength: Map<Long, List<Int>>): MatchData {
        val rangeEncoded = corpus.rangeEncoded[soughtLen] ?: return noMatch(soughtLen)
        // range = (x + 1) * 26 - x
        //  step 1: 26x + 26 - x
        //  step 2: 25x + 26
        //  step 3: profit!
        val range = rangeEncoded.first * 25 + 26
        val randInRange = Random.nextLong(range)
        val encoded = rangeEncoded.first + randInRange
        val matchEncoded = forLength[encoded] ?: return noMatch(soughtLen)
        return if (matchEncoded.isNotEmpty()) {
            val word = StringEncoder.decode(encoded)
            val index = corpus.matched(word, encoded, soughtLen)
            match(soughtLen, index)
        } else {
            noMatch(soughtLen)
        }
    }

    override fun check(): MatchData {
        val length = randomLength()
        val soughtLen = length - 1
        val forLength = corpus.numbers[soughtLen]
        return if (forLength == null) noMatch(soughtLen) else findMatch(soughtLen, forLength)
    }
}