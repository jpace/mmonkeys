package org.incava.mmonkeys.exec

import org.incava.mmonkeys.word.Word
import kotlin.random.Random

class WordVsStringSimulation(private val endChar: Char, private val sought: String) {
    fun run() {
        val str = StringSimulation(endChar, sought)
        val word = WordSimulation(endChar, Word(sought))
        repeat(10) {
            val b = Random.Default.nextBoolean()
            if (b) {
                str.run()
            } else {
                word.run()
            }
        }
    }
}