package org.incava.mmonkeys.mky.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.number.NumberedCorpus
import org.incava.mmonkeys.mky.number.NumbersMonkey
import org.incava.mmonkeys.type.Typewriter

private class MonkeyCheckTrials {
    val words = listOf("ab", "cd", "def", "defg", "ghi")

    fun monkeyCheck(monkey: Monkey) {
        var iterations = 0
        while (monkey.corpus.hasUnmatched()) {
            monkey.check()
            iterations++
        }
        Console.info("iterations", iterations)
    }

    fun mapMonkeyCheck() {
        Console.info("map")
        val corpus = MapCorpus(words)
        val obj = MapMonkeyUtils.createMapMonkey(1, corpus)
        monkeyCheck(obj)
    }

    fun eqMonkeyCheck() {
        Console.info("eq")
        val corpus = Corpus(words)
        val typewriter = Typewriter()
        val obj = EqMonkey(1, typewriter, corpus)
        monkeyCheck(obj)
    }

    fun numberedMonkeyCheck() {
        Console.info("numbers")
        val corpus = NumberedCorpus(words)
        val typewriter = Typewriter()
        val obj = NumbersMonkey(1, typewriter, corpus)
        monkeyCheck(obj)
    }
}

fun main() {
    val obj = MonkeyCheckTrials()
    obj.mapMonkeyCheck()
    obj.eqMonkeyCheck()
    obj.numberedMonkeyCheck()
}