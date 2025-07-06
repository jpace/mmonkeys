package org.incava.mmonkeys.rand

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Test

class Char3RandomTest {
    val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE).subList(0, 100)

    @Test
    fun mapping() {
        val chars = words.flatMap { word -> word.map { char -> char } + ' ' }
        val counted = mutableMapOf<Char, Int>()
        chars.forEach { counted[it] = (counted[it] ?: 0) + 1 }
        val obj = DistributedRandom(counted)
        Qlog.info("obj", obj)
        Qlog.info("obj.nextRandom()", obj.nextRandom())
    }

    @Test
    fun createFirsts3() {
        val sequences = SequencesFactory.createFromWords(words)
        val result = sequences.threes.dists().firsts
        Qlog.info("result", result.slots.toSortedMap())
    }

    @Test
    fun createSeconds3() {
        val sequences = SequencesFactory.createFromWords(words)
        val result = sequences.threes.dists().seconds
        Qlog.info("result", result.toSortedMap())
    }

    @Test
    fun createThirds3() {
        val sequences = SequencesFactory.createFromWords(words)
        val result = sequences.threes.dists().thirds
        Qlog.info("result", result.toSortedMap())
    }
}