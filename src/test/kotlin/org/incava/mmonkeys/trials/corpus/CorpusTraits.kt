package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console.printf
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.testutil.ResourceUtil
import java.math.BigInteger

class CorpusTraits {
}

fun main(args: Array<String>) {
    val file = ResourceUtil.getResourceFile("pg100.txt")
    val words = CorpusFactory.readFileWords(file, -1)
    println("words.#: ${words.size}")
    val bySize = words.groupBy { it.length }
    bySize.toSortedMap().forEach { (length, words) ->
        println("$length: ${words.size} (total)")
        println("$length: ${words.toSet().size} (unique)")
        val y = BigInteger.valueOf(26L)
        val z = y.pow(length)
        System.out.printf("z: %s\n", z)
        if (words.size < 5) {
            println("words: $words")
        }
        val seconds = z
        println("seconds: $seconds")
        val minutes = seconds / BigInteger.valueOf(60)
        println("minutes: $minutes")
        val hours = minutes / BigInteger.valueOf(60)
        println("hours: $hours")
        val days = hours / BigInteger.valueOf(24)
        println("days: $days")
        val years = days / BigInteger.valueOf(365)
        println("years: $years")
        printf("years %,d\n", years)
        println()
    }
}