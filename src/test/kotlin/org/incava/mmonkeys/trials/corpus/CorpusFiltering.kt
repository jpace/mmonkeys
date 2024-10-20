package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.testutil.ResourceUtil

fun main() {
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE, -1)
    println("words.#: ${words.size}")

    val downers = words.map { it.toLowerCase() }.distinct()
    val chars = ('a'..'z')
    chars.forEach { ch ->
        Console.info("ch", ch)
        val two = ch.toString() + ch
        val three = two + ch

        var hasTwo = false
        var hasThree = false

        downers.forEach { word ->
            if (word.contains(two)) {
                hasTwo = true
                if (word.contains(three)) {
                    hasThree = true
                }
            }
        }

        Console.info("hasTwo", hasTwo)
        Console.info("hasThree", hasThree)
    }

    chars.forEach { first ->
        chars.forEach { second ->
            val str = "$first$second"
            val match = downers.find { it.contains(str) }
            if (match == null) {
                Console.info("not found", str)
            } else {
                Console.info("found $str", match)
            }
        }
    }
}