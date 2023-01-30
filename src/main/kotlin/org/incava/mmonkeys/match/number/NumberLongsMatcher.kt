package org.incava.mmonkeys.match.number

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.MatchData
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.util.Console

class NumberLongsMatcher(monkey: Monkey, val sought: Corpus) : Matcher(monkey) {
    // length to [ encoded to [ indices in sought ] ]
    val numbers: MutableMap<Int, MutableMap<Long, MutableList<Int>>> = mutableMapOf()

    init {
        val encoded = mutableMapOf<String, Long>()
        sought.words.withIndex().forEach { word ->
            val enc = encoded.computeIfAbsent(word.value, StringEncoder::encodeToLong)
            numbers
                .computeIfAbsent(word.value.length) { mutableMapOf() }
                .computeIfAbsent(enc) { mutableListOf() }.also { it.add(word.index) }
        }
        Console.info("numbers", numbers)
        showUnmatched()
    }

    override fun isComplete(): Boolean {
        return numbers.isEmpty()
    }

    override fun check(): MatchData {
        // number of keystrokes at which we'll hit the end-of-word character
        // thus length == 1 means we'll hit at the first invocation, with
        // an empty string
        val length = rand.nextRand()
        val soughtLen = length - 1
        val forLength = numbers[soughtLen]
        if (forLength != null) {
            val num = monkey.nextLong(soughtLen)
            val forEncoded = forLength[num]
            if (forEncoded != null && forEncoded.isNotEmpty()) {
                val word = StringEncoder.decode(num)
                Console.info("word", word)
                val index = forEncoded.removeAt(0)
                // Console.info("index", index)
                // this is the index into sought
                if (forEncoded.isEmpty()) {
                    forLength.remove(num)
                }

                if (forLength.isEmpty()) {
                    numbers.remove(soughtLen)
                }
                showUnmatched()
                // showNumbers()
                // ugh -- need the index from `sought`
                return match(length, 0)
            }
        }
        return noMatch(length)
    }

    fun showUnmatched() {
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