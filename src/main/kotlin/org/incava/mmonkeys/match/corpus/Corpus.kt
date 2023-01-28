package org.incava.mmonkeys.match.corpus

class Corpus(val words: List<String>) {
    private val matched = mutableSetOf<Int>()

    fun hasWord(word: String): Pair<Boolean, Int> {
        val index = words.indexOf(word)
        return Pair(index >= 0, index)
    }

    fun matchWord(word: String): Int? {
        val index = words.indexOf(word)
        return if (index >= 0) index else null
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

    fun isEmpty(): Boolean {
        return matched.size == words.size
    }

    fun isNotEmpty() = !isEmpty()

    fun write() {
//        words.withIndex().forEach { (index, value) ->
//            if (index != 0) {
//                print(" ")
//            }
//            print(value)
//        }
//        println()
        words.withIndex().forEach { (index, value) ->
            if (index != 0) {
                print(" ")
            }
            if (matched.contains(index)) print(value) else print(value.replace(Regex("."), "-"))
        }
        println()
    }
}