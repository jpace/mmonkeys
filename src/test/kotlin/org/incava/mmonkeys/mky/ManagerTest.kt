package org.incava.mmonkeys.mky

import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.corpus.WordCorpus
import org.incava.mmonkeys.mky.mgr.ManagerFactory
import org.incava.mmonkeys.mky.mind.RandomStrategy
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.util.ResourceUtil
import org.incava.mmonkeys.words.Attempts
import org.incava.mmonkeys.words.Word
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ManagerTest {
    @Test
    fun recordWords() {
        val corpus = WordCorpus(CorpusFactory.fileToWords(ResourceUtil.FULL_FILE))
        val manager = ManagerFactory.createWithoutView(corpus)
        val factory = DefaultMonkeyFactory(manager, corpus)
        val strategy = RandomStrategy(Keys.fullList())
        val monkey = factory.createMonkey(strategy)
        val attempts1 = listOf("this" to 3, "is" to 17, "a" to 9, "test" to 6)
            .map { Word(it.first, it.second) }
            .let { Attempts(it, 1, 2) }
        assertEquals(0L,manager.keystrokesCount())
        manager.update(monkey, attempts1)
        assertEquals(1L, manager.keystrokesCount())
        val attempts2 = listOf("also" to 4, "this" to 1, "instance" to 9)
            .map { Word(it.first, it.second) }
            .let { Attempts(it, 4, 6) }
        assertEquals(1L, manager.keystrokesCount())
        manager.update(monkey, attempts2)
        assertEquals(5L, manager.keystrokesCount())
    }
}