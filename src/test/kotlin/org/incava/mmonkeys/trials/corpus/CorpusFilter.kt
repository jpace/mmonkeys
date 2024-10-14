package org.incava.mmonkeys.trials.corpus

class CorpusFilter(words: Collection<String>) {
    val downers = words.map { it.toLowerCase() }.distinct()
    val chars = ('a'..'z')
    val missingTwos: MutableMap<Char, MutableSet<Char>>
    val missingThrees: MutableMap<Char, MutableMap<Char, MutableSet<Char>>>

    init {
        missingTwos = mutableMapOf()
        missingThrees = mutableMapOf()
        val chars = ('a'..'z')

        chars.forEach { first ->
            chars.forEach { second ->
                val str2 = "$first$second"
                val match2 = downers.find { it.contains(str2) }
                if (match2 == null) {
                    missingTwos.computeIfAbsent(first) { mutableSetOf() }.also { it.add(second) }
                }
                chars.forEach { third ->
                    val str3 = str2 + third
                    val match3 = downers.find { it.contains(str3) }
                    if (match3 == null) {
                        missingThrees.computeIfAbsent(first) {
                            mutableMapOf()
                        }.computeIfAbsent(second) { mutableSetOf() }.also { it.add(third) }
                    }
                }
            }
        }
    }

    fun canMatch(ch1: Char, ch2: Char): Boolean {
        return false
    }
}