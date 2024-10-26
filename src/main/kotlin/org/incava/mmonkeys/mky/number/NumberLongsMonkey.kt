package org.incava.mmonkeys.mky.number

import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.mky.corpus.CorpusMonkey
import org.incava.mmonkeys.type.Typewriter
import kotlin.random.Random

class NumberLongsMonkey(override val corpus: NumberedCorpus, id: Int, typewriter: Typewriter) :
    CorpusMonkey(corpus, id, typewriter) {
    private val verbose = false

    fun findMatch(soughtLen: Int, forLength: Map<Long, List<Int>>): MatchData {
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

    private fun showUnmatched() {
        if (!verbose) {
            return
        }

        // val str = String.format("%8d | %8d | %8d | %8d", )
        val lenToCount = lengthToCount()
        val total = lenToCount.values.sum()
        val str = lenToCount
            .entries
            .joinToString(", ") { entry -> entry.key.toString() + ": " + entry.value }
        println("total: $total, $str")
    }

    private fun lengthToCount(): Map<Int, Int> {
        return corpus.numbers.toSortedMap()
            .entries.associate { entry -> entry.key to entry.value.map { it.value.size }.sum() }
    }

    fun showNumbers() {
        corpus.numbers.forEach { (length, numbers) ->
            println(length)
            numbers.forEach { (number, indices) ->
                val str = StringEncoder.decode(number)
                println("  $str")
                println("    " + indices.joinToString(", "))
            }
        }
    }
}