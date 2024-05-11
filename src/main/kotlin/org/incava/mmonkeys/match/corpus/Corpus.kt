package org.incava.mmonkeys.match.corpus

class Corpus(val words: List<String>) {
    private val matched = mutableSetOf<Int>()

    fun hasWord(word: String): Pair<Boolean, Int> {
        val index = words.indexOf(word)
        return Pair(index >= 0, index)
    }

    fun remove(word: String) {
        words.withIndex().forEach { (index, str) ->
            if (!matched.contains(index) && str == word) {
                matched.add(index)
                return
            }
        }
    }

    fun removeAt(index: Int) = matched.add(index)

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
}