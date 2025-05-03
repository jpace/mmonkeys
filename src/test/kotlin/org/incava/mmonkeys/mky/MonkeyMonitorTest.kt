package org.incava.mmonkeys.mky

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.dc.DualCorpus
import org.incava.mmonkeys.mky.corpus.dc.WordsGeneratorMonkeyFactory
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.util.ResourceUtil
import org.incava.mmonkeys.words.Attempt
import org.incava.mmonkeys.words.Word
import org.incava.mmonkeys.words.Words
import kotlin.test.Test
import kotlin.test.assertEquals

internal class MonkeyMonitorTest {
    @Test
    fun recordWords() {
        val monitor = object : MonkeyMonitor {
            var totalKeystrokes = 0L

            override fun update(monkey: Monkey, attempt: Attempt) {
                Console.info("monkey", monkey)
                Console.info("words", attempt)
                totalKeystrokes += attempt.totalKeyStrokes
            }

            override fun summarize() {
                TODO("Not yet implemented")
            }

            override fun attemptCount(): Long {
                TODO("Not yet implemented")
            }

            override fun matchCount(): Int {
                TODO("Not yet implemented")
            }

            override fun keystrokesCount(): Long {
                TODO("Not yet implemented")
            }

            override fun matchesByLength(): Map<Int, Int> {
                TODO("Not yet implemented")
            }
        }
        val corpus = DualCorpus(CorpusFactory.fileToWords(ResourceUtil.FULL_FILE))
        val manager = Manager(corpus)
        val monkey = WordsGeneratorMonkeyFactory.createMonkey(1, corpus).also { it.manager = manager }
        val words1 = listOf("this" to 3, "is" to 17, "a" to 9, "test" to 6)
            .map { Word(it.first, it.second) }
            .let { Words(it, 100, 10) }
        assertEquals(0L, monitor.totalKeystrokes)
        monitor.update(monkey, words1)
        assertEquals(100L, monitor.totalKeystrokes)
        val words2 = listOf("also" to 4, "this" to 1, "instance" to 9)
            .map { Word(it.first, it.second) }
            .let { Words(it, 42, 10) }
        assertEquals(100L, monitor.totalKeystrokes)
        monitor.update(monkey, words2)
    }
}