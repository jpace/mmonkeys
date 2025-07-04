package org.incava.mmonkeys.mky

import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.corpus.WordCorpus
import org.incava.mmonkeys.mky.mgr.ManagerFactory
import org.incava.mmonkeys.mky.mind.RandomStrategy
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.util.ResourceUtil
import org.incava.mmonkeys.words.Attempt
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
        val attempt1 = Attempt(Word("this", 3), 5)
        assertEquals(0L, manager.keystrokesCount())
        manager.update(monkey, attempt1)
        assertEquals(5L, manager.keystrokesCount())
        val attempt2 = Attempt(Word("is", 17), 3)
        manager.update(monkey, attempt2)
        assertEquals(8L, manager.keystrokesCount())
    }
}