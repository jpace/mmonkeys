package org.incava.mmonkeys.mky

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.dc.DualCorpus
import org.incava.mmonkeys.mky.corpus.dc.WordsGeneratorMonkeyManager
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.util.ResourceUtil
import org.incava.mmonkeys.words.Attempt
import org.incava.mmonkeys.words.Attempts
import org.incava.mmonkeys.words.Word
import kotlin.test.Test
import kotlin.test.assertEquals

internal class MonkeyMonitorTest {
    @Test
    fun recordWords() {
        val monitor = object : MonkeyMonitor {
            var totalKeystrokes = 0L

            override fun update(monkey: Monkey, attempt: Attempt) {
                Console.info("monkey", monkey)
                Console.info("attempt", attempt)
                totalKeystrokes += attempt.totalKeyStrokes
            }

            override fun update(monkey: Monkey, attempts: Attempts) {
                Console.info("monkey", monkey)
                Console.info("attempts", attempts)
                totalKeystrokes += attempts.totalKeyStrokes
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
        val wordMonkeyManager = WordsGeneratorMonkeyManager(corpus)
        val monkey = wordMonkeyManager.createMonkey().also { it.manager = manager }
        val attempts1 = listOf("this" to 3, "is" to 17, "a" to 9, "test" to 6)
            .map { Word(it.first, it.second) }
            .let { Attempts(it, 1, 2) }
        assertEquals(0L, monitor.totalKeystrokes)
        monitor.update(monkey, attempts1)
        assertEquals(1L, monitor.totalKeystrokes)
        val attempts2 = listOf("also" to 4, "this" to 1, "instance" to 9)
            .map { Word(it.first, it.second) }
            .let { Attempts(it, 4, 6) }
        assertEquals(1L, monitor.totalKeystrokes)
        monitor.update(monkey, attempts2)
        assertEquals(5L, monitor.totalKeystrokes)
    }
}