package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.ikdk.math.Maths
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.util.ResourceUtil
import java.math.BigInteger
import kotlin.math.pow

class CorpusFilterTrial {
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)

    fun duplicates() {
        val obj = CorpusFilter(words)
        val result = obj.missingTwos
        result.forEach { (a, nexts) ->
            if (nexts.contains(a)) {
                Console.info("$a")
            }
        }
    }

    fun twos() {
        val obj = CorpusFilter(words)
        val result = obj.missingTwos
        result.forEach { (a, bs) ->
            bs.forEach { b ->
                Console.info("$a$b")
            }
        }
    }

    fun threes() {
        val obj = CorpusFilter(words)
        val result = obj.missingThrees
        result.forEach { (a, bcs) ->
            bcs.forEach { (b, cs) ->
                cs.forEach { c -> Console.info("$a$b$c") }
            }
        }
    }

    fun percentPresent() {
        val unique = words.toSet()

        System.out.printf(
            "%3s | %12s | %12s | %16s | %16s | %16s | %s\n",
            "len", "unique.#", "total.#", "uniq / total", "uniq / poss", "total / poss", "possible.#"
        )

        (1..30).forEach { length ->
            val totalCount = words.filter { it.length == length }.size
            val uniqueCount = unique.filter { it.length == length }.size
            if (length > 13) {
                showStats(length, totalCount, uniqueCount)
            } else {
                val possible = 26.0.pow(length)
                System.out.printf(
                    "%-3d | %,12d | %,12d | %16.10f | %16.10f | %16.10f | %32s\n",
                    length, uniqueCount, totalCount,
                    uniqueCount.toDouble() / totalCount,
                    uniqueCount.toDouble() / possible,
                    totalCount.toDouble() / possible,
                    possible.toLong()
                )
            }
            println()
        }
    }

    fun showStats(length: Int, totalCount: Int, uniqueCount: Int) {
        val possible = Maths.powerBigRepeat(BigInteger.valueOf(26), length).toBigDecimal()
        val uniqPct = uniqueCount.toBigDecimal() / possible
        System.out.printf(
            "%-3d | %,12d | %,12d | %16.10f | %16.10f | %16.10f | %32s\n",
            length, uniqueCount, totalCount,
            uniqueCount.toDouble() / totalCount,
            uniqueCount.toBigDecimal() / possible,
            totalCount.toBigDecimal() / possible,
            possible.toBigInteger()
        )
    }

    fun charFrequency() {
        val byChar = mutableMapOf<Char, Int>()
        var numChars = 0
        words.forEach { word ->
            word.toCharArray().forEach { ch ->
                byChar[ch] = (byChar[ch] ?: 0) + 1
                ++numChars
            }
        }
        println("byChar: ${byChar.toSortedMap()}")
        byChar.toSortedMap().forEach { (ch, count) ->
            println("$ch: ${100.0 * count / numChars}")
        }
    }
}

fun main() {
    val obj = CorpusFilterTrial()
    if (false) {
        obj.duplicates()
        obj.percentPresent()
        obj.twos()
        obj.threes()
    }
    obj.charFrequency()
}