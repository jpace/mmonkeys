package org.incava.mmonkeys.match.number

import org.incava.ikdk.io.Console
import org.incava.ikdk.math.Maths
import org.incava.mmonkeys.match.MatchData
import org.incava.mmonkeys.match.corpus.CorpusMonkey
import org.incava.mmonkeys.type.Typewriter
import kotlin.random.Random

class NumberLongsMonkey(val corpus: NumberedCorpus, id: Int, typewriter: Typewriter) : CorpusMonkey(corpus, id, typewriter) {
    private val charCount = typewriter.numChars().toLong() - 1
    val verbose = false

    init {
        Console.info("this", this)
        Console.info("sought", sought)
        if (verbose) {
            Console.info("corpus.numbers", corpus.numbers)
            showUnmatched()
        }
    }

    override fun check(): MatchData {
        val length = randomLength()
        val soughtLen = length - 1
        val forLength = corpus.numbers[soughtLen]
        if (forLength != null) {
            val num = randomLong(soughtLen)
            val forEncoded = forLength[num]
            if (!forEncoded.isNullOrEmpty()) {
                val word = StringEncoder.decode(num)
//                Console.info("word", word)
                val index = corpus.matched(word, num, soughtLen)
                showUnmatched()
                // showNumbers()
                return match(soughtLen, index)
            }
        }
        return noMatch(length)
    }

    private fun randomLong(digits: Int): Long {
        val max = Maths.powerLongCached(charCount, digits) * 2
        if (max <= 0) {
            Console.info("max", max);
            Console.info("charCount", charCount);
            Console.info("digits", digits);
            throw IllegalArgumentException("bound must be positive, not: $max")
        }
        return Random.nextLong(max)
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

    fun lengthToCount(): Map<Int, Int> {
        return corpus.numbers.toSortedMap()
            .entries
            .map { entry -> entry.key to entry.value.map { it.value.size }.sum() }
            .toMap()
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