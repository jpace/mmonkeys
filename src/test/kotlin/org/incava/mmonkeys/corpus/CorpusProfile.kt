package org.incava.mmonkeys.corpus

import org.incava.confile.Profiler
import org.incava.confile.SortType
import org.incava.mmonkeys.corpus.impl.ListCorpus
import org.incava.mmonkeys.corpus.impl.MapCorpus
import kotlin.random.Random

class CorpusProfile(private val numInvokes: Long, private val numTrials: Int) {
    val chars = ('a'..'z').toList()
    val words = CorpusFactory.defaultWords()

    fun getRandoms(): List<String> {
        val numTotal = 1_000_000
        val numValid = 1_000
        return (0..numTotal).fold(mutableListOf<String>()) { list, _ ->
            val length = Random.Default.nextInt(10)
            val word = (0..length).fold(StringBuilder()) { sb, _ -> sb.append(chars.random()) }.toString()
            list += word
            list
        } + (0..numValid).fold(mutableListOf()) { list, _ -> list += words.random(); list }
    }

    fun find() {
        val profiler = Profiler(numInvokes, numTrials)

        val corpus1 = ListCorpus(words)
        val corpus2 = MapCorpus(words)
        val randoms = getRandoms()

        profiler.add("list find match") {
            val word = randoms.random()
            val idx = corpus1.findMatch(word)
        }

        profiler.add("map find match") {
            val word = randoms.random()
            val idx = corpus2.findMatch(word)
        }

        profiler.add("list find word") {
            val word = randoms.random()
            val idx = corpus1.findWord(word)
        }

        profiler.add("map find word") {
            val word = randoms.random()
            val idx = corpus2.findWord(word)
        }

        profiler.runAll()
        profiler.showResults(SortType.BY_INSERTION)
    }

    fun findWord() {
        val profiler = Profiler(numInvokes, numTrials)

        val corpus1 = ListCorpus(words)
        val corpus2 = MapCorpus(words)
        val randoms = getRandoms()

        profiler.add("list find word") {
            val word = randoms.random()
            val idx = corpus1.findWord(word)
        }

        profiler.add("map find word") {
            val word = randoms.random()
            val idx = corpus2.findWord(word)
        }

        profiler.runAll()
        profiler.showResults(SortType.BY_INSERTION)
    }
}

fun main() {
    val obj = CorpusProfile(10_000L, 3)
    obj.find()
//    obj.findWord()
}