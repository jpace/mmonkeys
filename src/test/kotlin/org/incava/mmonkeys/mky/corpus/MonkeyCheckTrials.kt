package org.incava.mmonkeys.mky.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.number.NumberedCorpus
import org.incava.mmonkeys.mky.number.NumbersMonkey
import org.incava.mmonkeys.type.Typewriter

class MonkeyCheckTrials {
    val words = listOf("ab", "cd", "def", "defg", "ghi")

    fun mapMonkeyCheck() {
        Console.info("map")
        val corpus = MapCorpus(words)
        val typewriter = Typewriter()
        val obj = MapMonkey(1, typewriter, corpus)
        var iterations = 0
        while (!obj.corpus.isEmpty()) {
            obj.check()
            iterations++
        }
        Console.info("iterations", iterations)
        assert(obj.corpus.isEmpty())
    }

    fun eqMonkeyCheck() {
        Console.info("eq")
        val corpus = Corpus(words)
        val typewriter = Typewriter()
        val obj = EqMonkey(1, typewriter, corpus)
        var iterations = 0
        while (!obj.corpus.isEmpty()) {
            obj.check()
            iterations++
        }
        Console.info("iterations", iterations)
        assert(obj.corpus.isEmpty())
    }

    fun numberedMonkeyCheck() {
        Console.info("numbers")
        val corpus = NumberedCorpus(words)
        val typewriter = Typewriter()
        val obj = NumbersMonkey(1, typewriter, corpus)
        var iterations = 0
        while (!obj.corpus.isEmpty()) {
            obj.check()
            iterations++
        }
        Console.info("iterations", iterations)
        assert(obj.corpus.isEmpty())
    }
}

fun main() {
    val obj = MonkeyCheckTrials()
    obj.mapMonkeyCheck()
    obj.eqMonkeyCheck()
    obj.numberedMonkeyCheck()
}