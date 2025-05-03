package org.incava.mmonkeys.rand

import org.incava.ikdk.io.Qlog

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

    fun toCharsList(words: List<String>): List<Char> {
        return words.map(String::lowercase)
            .fold(mutableListOf()) { acc, char ->
                if (acc.isNotEmpty()) {
                    // Qlog.info("char", char)
                    acc += ' ';
                }
                acc.addAll(char.toCharArray().toList())
                acc
            }
    }
}