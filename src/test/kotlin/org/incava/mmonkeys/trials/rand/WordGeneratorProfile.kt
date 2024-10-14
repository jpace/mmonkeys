package org.incava.mmonkeys.trials.rand

import org.incava.confile.Profiler
import org.incava.confile.SortType
import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.MapCorpus
import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.mmonkeys.trials.corpus.CorpusFilter
import org.incava.rando.RandSlotsFactory

class RepeatCharFilter : GenFilter {
    val noTwos = listOf('j', 'k', 'q', 'v', 'w', 'x', 'y')
    var prev: Char? = null

    override fun check(ch: Char): Boolean {
        return if (ch == prev && noTwos.contains(ch)) {
            false
        } else {
            prev = ch
            true
        }
    }
}

private class WordGeneratorProfile(private val numInvokes: Long, private val trialInvokes: Int = 5) {
    fun profile() {
        val slots = RandSlotsFactory.calcArray(StrRand.Constants.NUM_CHARS + 1, 128, 100_000)
        val generator1 = StrToggleAnyRand(slots)
        val generator2 = StrRandFactory.create(128, StrRandFactory.calcArray, StrRandFactory.assemble)
        val generator3 = StrRandFiltered(slots)
        val wordsGenerator1 = WordsGenerator(slots, generator1)
        val wordsGenerator2 = WordsGenerator(slots, generator2)
        val wordsGenerator3 = WordsGenerator(slots, generator3)
        val file = ResourceUtil.getResourceFile("pg100.txt")
        val words = CorpusFactory.readFileWords(file, -1)
        val filter = CorpusFilter(words)
        val mapCorpus = MapCorpus(words)
        val profiler = Profiler(numInvokes, trialInvokes)

        profiler.add("toggle any") { wordsGenerator1.generate() }
        profiler.add("calc array assemble") { wordsGenerator2.generate() }
        profiler.add("filtered 1") { wordsGenerator3.generate(::RepeatCharFilter) }
        profiler.add("filtered 2") { wordsGenerator3.generate { RepeatCharFilter2(filter) } }
        profiler.add("filtered 3") { wordsGenerator3.generate2 { KnownWordFilter(mapCorpus, it) } }

        profiler.runAll()
        profiler.showResults(SortType.BY_DURATION)

        val showdown = profiler.spawn()
        showdown.runAll()
        showdown.showResults(SortType.BY_DURATION)
    }
}

fun main() {
    val obj = WordGeneratorProfile(100L, 3)
    obj.profile()
}