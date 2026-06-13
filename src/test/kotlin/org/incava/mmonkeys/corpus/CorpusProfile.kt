package org.incava.mmonkeys.corpus

import org.incava.confile.Profiler
import org.incava.confile.SortType
import org.incava.mmonkeys.corpus.dag.DagCorpus

class CorpusProfile(numInvokes: Long, numTrials: Int) {
    private val words = CorpusFactory.defaultWords()
    private val randomsProvider = RandomsProvider(words)
    private val profiler = Profiler(numInvokes, numTrials)

    private fun <T, U> add(name: String, corpusSupplier: (List<String>) -> T, randSupplier: () -> U, wordFinder: (T, U) -> Any?) {
        val corpus = corpusSupplier(words)
        val blk: (T) -> Unit = {
            val word = randSupplier()
            wordFinder(corpus, word)
        }
        val blk2 = { blk(corpus) }
        profiler.add(name, blk2)
    }

    fun find() {
        val randoms27 = randomsProvider.getRandoms(27)
        val randoms7 = randomsProvider.getRandoms(7)

        val rand27: () -> String = { randoms27.random() }
        val rand7: () -> String = { randoms7.random() }
        val finder: (Corpus, String) -> Int? = { corpus, word -> corpus.findMatch(word) }

        add("word match 27", ::WordCorpus, rand27, finder)
        add("word match 7", ::WordCorpus, rand7, finder)

        add("dag 27", ::DagCorpus, rand27, finder)
        add("dag 7", ::DagCorpus, rand7, finder)

        profiler.runAll()
        profiler.showResults(SortType.BY_NAME)

        val showdown = profiler.spawn()
        val stats = showdown.runAll()
        showdown.showResults(SortType.BY_NAME)

        if (stats.durations.values.minOf { it } < 1000L) {
            val showdown2 = showdown.spawn()
            showdown2.runAll()
            profiler.showResults(SortType.BY_NAME)
            println()
            showdown.showResults(SortType.BY_NAME)
            println()
            showdown2.showResults(SortType.BY_NAME)
        }
    }
}

fun main() {
    val includeList = false
    val obj = CorpusProfile(if (includeList) 1000 else 10_000_000, 2)
    obj.find()
}