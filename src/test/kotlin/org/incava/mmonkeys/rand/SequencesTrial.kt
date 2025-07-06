package org.incava.mmonkeys.rand

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@Disabled
class SequencesTrial {
    @Test
    fun testingPair() {
        val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE)
        val chars = SequencesFactory.toCharsList(words)
        Qlog.info("chars.#", chars.size)
        val result = (1 until chars.size).map { index ->
            val x = chars[index - 1]
            val y = chars[index]
            Pair(x, y)
        }
        Qlog.info("result.#", result.size)
        val counted = result.fold(mutableMapOf<Pair<Char, Char>, Int>()) { acc, n ->
            acc[n] = (acc[n] ?: 0) + 1
            acc
        }
        Qlog.info("counted", counted)
    }

    @Test
    fun testingTriple() {
        val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE)
        val chars = SequencesFactory.toCharsList(words)
        Qlog.info("chars.#", chars.size)
        val result = (2 until chars.size).map { index ->
            val x = chars[index - 2]
            val y = chars[index - 1]
            val z = chars[index]
            val tuple = Triple(x, y, z)
            tuple
        }
        Qlog.info("result.#", result.size)
        val counted = result.fold(mutableMapOf<Triple<Char, Char, Char>, Int>()) { acc, n ->
            acc[n] = (acc[n] ?: 0) + 1
            acc
        }
        Qlog.info("counted", counted)
    }

    @Test
    fun testingTwo() {
        val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE)
        val chars = SequencesFactory.toCharsList(words)
        Qlog.info("chars.#", chars.size)
        val result = toCountedMap(chars, 2)
        Qlog.info("result", result)
    }

    @Test
    fun testingThree() {
        val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE)
        val chars = SequencesFactory.toCharsList(words)
        Qlog.info("chars.#", chars.size)
        val result = toCountedMap(chars, 3)
        Qlog.info("result", result)
    }

    @Test
    fun testingFour() {
        val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE)
        val chars = SequencesFactory.toCharsList(words)
        Qlog.info("chars.#", chars.size)
        val result = toCountedMap(chars, 4)
        Qlog.info("result", result)
    }

    fun toList(list: List<Char>, size: Int): List<List<Char>> {
        val offset = size - 1
        return (offset until list.size).map { index ->
            list.subList(index - offset, index + 1)
        }
    }

    fun toCountedMap(list: List<Char>, size: Int): Map<List<Char>, Int> {
        val offset = size - 1
        return (offset until list.size).fold(mutableMapOf()) { acc, index ->
            val chars = list.subList(index - offset, index + 1)
            acc[chars] = (acc[chars] ?: 0) + 1
            acc
        }
    }

    @Test
    fun testToList() {
        val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE).subList(0, 100)
        val chars = SequencesFactory.toCharsList(words)
        Qlog.info("chars", chars)
        val result = toList(chars, 2)
        Qlog.info("result.#", result.size)
    }

    @Test
    fun testingFive() {
        val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE)
        val chars = SequencesFactory.toCharsList(words)
        Qlog.info("chars.#", chars.size)
        val result = toCountedMap(chars, 5)
        Qlog.info("result", result)
    }
}