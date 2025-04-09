package org.incava.mmonkeys.rand

object SequencesFactory {
    fun createFromWords(words: List<String>): Sequences {
        val chars = words.map(String::lowercase)
            .fold(mutableListOf<Char>()) { acc, char ->
                if (acc.isNotEmpty()) {
                    acc += ' ';
                }
                acc.addAll(char.toCharArray().toList())
                acc
            }
        return Sequences(chars)
    }
}