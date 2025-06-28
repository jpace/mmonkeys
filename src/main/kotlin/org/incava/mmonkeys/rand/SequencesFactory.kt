package org.incava.mmonkeys.rand

object SequencesFactory {
    fun createFromWords(words: List<String>): Sequences {
        val chars = toCharsList(words)
        return Sequences(chars)
    }

    fun toCharsList(words: List<String>): List<Char> {
        return words.map(String::lowercase)
            .fold(mutableListOf()) { acc, char ->
                if (acc.isNotEmpty()) {
                    acc += ' ';
                }
                acc.also { it.addAll(char.toCharArray().toList()) }
            }
    }
}