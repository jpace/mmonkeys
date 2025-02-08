package org.incava.mmonkeys.mky.corpus.sc

import org.incava.mmonkeys.type.Keys
import java.util.*

class SequenceFinder(words: List<String>) {
    val presentTwos: Map<Char, List<Char>>

    init {
        val downers = words.map { it.lowercase(Locale.getDefault()) }.distinct()
        val chars = Keys.alphaList()
        presentTwos = chars.associateWith { first ->
            chars.filter { second ->
                val str = "$first$second"
                downers.any { it.contains(str) }
            } + Keys.END_CHAR
        }
    }
}