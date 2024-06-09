package org.incava.mmonkeys.match.corpus

import org.incava.ikdk.io.Console

class Corpus(val words: List<String>) {
    val matched = mutableSetOf<Int>()

    fun match(word: String): Int {
        words.indices.forEach { index ->
            // not sure which condition is faster:
            if (words[index] == word && !matched.contains(index)) {
                return index
            }
        }
        return -1
    }

    fun remove(word: String) {
        words.withIndex().forEach { (index, str) ->
            if (!matched.contains(index) && str == word) {
                matched.add(index)
                return
            }
        }
    }

    fun removeAt(index: Int) {
        matched.add(index)
    }

    fun hasUnmatched(): Boolean = matched.size < words.size

    fun isEmpty(): Boolean = matched.size == words.size

    fun write() {
        words.withIndex().forEach { (index, word) ->
            if (index > 0) {
                if (index % 24 == 0)
                    println()
                else
                    print(" ")
            }
            val str = if (matched.contains(index))
                word
            else
                "-".repeat(word.length)
            print(str)
        }
        println()
    }

    fun summarize() {
        val byLength = words.fold(mutableMapOf<Int, Int>()) { acc, word ->
            acc.merge(word.length, 1) { prev, _ -> prev + 1 }
            acc
        }
        byLength.toSortedMap().forEach { (length, count) ->
            Console.info("length", length)
            Console.info("count", count)
        }

        val unique = words.toSet()
        unique.toSortedSet().forEach { word ->
            Console.info("word", word)
        }

        val wordToCount = words.fold(mutableMapOf<String, Int>()) { acc, word ->
            acc.merge(word, 1) { prev, _ -> prev + 1 }
            acc
        }
        wordToCount.toSortedMap().forEach { (word, count) ->
            Console.info("word", word)
            Console.info("count", count)
        }

    }
}
