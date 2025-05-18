package org.incava.mmonkeys.rand

object SequencesFactory {
    fun createFromWords(words: List<String>): Sequences {
        val chars = words.map(String::lowercase)
            .fold(mutableListOf<Char>()) { acc, char ->
                if (acc.isNotEmpty()) {
                    acc += ' ';
                }
                acc.also { it.addAll(char.toCharArray().toList()) }
            }
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