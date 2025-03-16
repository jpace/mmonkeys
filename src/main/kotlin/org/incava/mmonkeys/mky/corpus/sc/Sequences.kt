package org.incava.mmonkeys.mky.corpus.sc

class Sequences(words: List<String>) {
    val presentTwos: MutableMap<Char, MutableSet<Char>>
    val presentTwosCounted: MutableMap<Char, MutableMap<Char, Int>>
    val presentThrees: MutableMap<Char, MutableMap<Char, MutableList<Char>>>
    val presentThreesCounted: MutableMap<Char, MutableMap<Char, MutableMap<Char, Int>>>

    init {
        val asChars = mutableListOf<Char>()
        words.forEach { word ->
            word.forEach { asChars += it.lowercaseChar() }
            asChars += ' '
        }
        presentTwos = mutableMapOf()
        presentThrees = mutableMapOf()
        presentTwosCounted = mutableMapOf()
        presentThreesCounted = mutableMapOf()
        (1 until asChars.size).forEach { index ->
            val prev = asChars[index - 1]
            val curr = asChars[index]
            presentTwos.computeIfAbsent(prev) { mutableSetOf() }.also { it.add(curr) }
            presentTwosCounted.computeIfAbsent(prev) { mutableMapOf() }
                .also { map ->
                    map.compute(curr) { _, count -> (count ?: 0) + 1 }
                }
            if (index > 1) {
                val prevPrev = asChars[index - 2]
                presentThrees.computeIfAbsent(prevPrev) { mutableMapOf() }
                    .also { second ->
                        second.computeIfAbsent(prev) { mutableListOf() }.also { third ->
                            third.add(curr)
                        }
                    }
                presentThreesCounted.computeIfAbsent(prevPrev) { mutableMapOf() }
                    .also { seconds ->
                        seconds.computeIfAbsent(prev) { mutableMapOf() }
                            .also { thirds -> thirds.compute(curr) { _, value -> (value ?: 0) + 1 } }
                    }

            }
        }
    }
}