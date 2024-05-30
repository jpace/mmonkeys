package org.incava.mmonkeys.match.number

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.MatchData
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.ikdk.io.Console
import org.incava.ikdk.math.Maths
import org.incava.mmonkeys.match.corpus.CorpusMatcher
import kotlin.random.Random

class NumberLongsMatcher(monkey: Monkey, sought: Corpus) : CorpusMatcher(monkey, sought) {
    // length to [ encoded to [ indices in sought ] ]
    val numbers: MutableMap<Int, MutableMap<Long, MutableList<Int>>> = mutableMapOf()
    private val charCount = monkey.typewriter.numChars().toLong() - 1
    val verbose = false

    init {
        val encoded = mutableMapOf<String, Long>()
        sought.words.withIndex().forEach { word ->
            val enc = encoded.computeIfAbsent(word.value, StringEncoder::encodeToLong)
            numbers
                .computeIfAbsent(word.value.length) { mutableMapOf() }
                .computeIfAbsent(enc) { mutableListOf() }.also { it.add(word.index) }
        }
        if (verbose) {
            Console.info("numbers", numbers)
            showUnmatched()
        }
    }

    override fun check(): MatchData {
        val length = randomLength()
        val soughtLen = length - 1
        val forLength = numbers[soughtLen]
        if (forLength != null) {
            val num = randomLong(soughtLen)
            val forEncoded = forLength[num]
            if (!forEncoded.isNullOrEmpty()) {
                val word = StringEncoder.decode(num)
//                Console.info("word", word)
                val index = forEncoded.removeAt(0)
//                Console.info("index", index)
//                Console.info("sought.word[$index]", sought.words[index])
                // this is the index into sought
                if (forEncoded.isEmpty()) {
                    forLength.remove(num)
                }
                if (forLength.isEmpty()) {
                    numbers.remove(soughtLen)
                }
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
        return numbers.toSortedMap()
            .entries
            .map { entry -> entry.key to entry.value.map { it.value.size }.sum() }
            .toMap()
    }

    fun showNumbers() {
        numbers.forEach { (length, numbers) ->
            println(length)
            numbers.forEach { (number, indices) ->
                val str = StringEncoder.decode(number)
                println("  $str")
                println("    " + indices.joinToString(", "))
            }
        }
    }
}