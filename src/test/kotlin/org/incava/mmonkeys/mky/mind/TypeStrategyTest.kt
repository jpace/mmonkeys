package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.corpus.impl.MapCorpus
import org.incava.mmonkeys.mky.DefaultMonkey
import org.incava.mmonkeys.mky.DefaultMonkeyManager
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.type.Keys
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class TypeStrategyTest {
    class DeterministicStrategy(private val chars: List<Char>) : TypeStrategy() {
        private var count = 0
        private val size = chars.size

        override fun getNextChar(): Char {
            return chars[count++ % size]
        }
    }

    @Test
    fun testRunIterationNoMatch() {
        val words = listOf("xyz")
        val toChar = 'e'
        val corpus = MapCorpus(words)
        val strategy = DeterministicStrategy(Keys.keyList(toChar))
        val manager = createManager(words, toChar)
        val mgr = DefaultMonkeyManager(manager, corpus)
        val obj = mgr.createMonkey(strategy)
        obj.type()
        assertEquals(0, manager.matchCount)
    }

    @Test
    fun testRunIterationMatch() {
        val words = listOf("abcde")
        val toChar = 'e'
        val corpus = MapCorpus(words)
        val strategy = DeterministicStrategy(Keys.keyList(toChar))
        val manager = createManager(words, toChar)
        val mgr = DefaultMonkeyManager(manager, corpus)
        val obj = mgr.createMonkey(strategy)
        obj.type()
        assertEquals(1, manager.matchCount)
    }

    private fun createManager(words: List<String>, toChar: Char): Manager {
        val corpus = MapCorpus(words)
        return Manager(corpus, 1)
    }

    private fun createMonkey(words: List<String>, toChar: Char): DefaultMonkey {
        val corpus = MapCorpus(words)
        val strategy = DeterministicStrategy(Keys.keyList(toChar))
        val manager = Manager(corpus, 1)
        val mgr = DefaultMonkeyManager(manager, corpus)
        return mgr.createMonkey(strategy)
    }
}