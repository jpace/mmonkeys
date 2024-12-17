package org.incava.mmonkeys.mky

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.dc.DualCorpusMonkey
import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.mmonkeys.words.Word
import org.incava.mmonkeys.words.Words
import org.junit.jupiter.api.assertAll
import kotlin.test.Test
import kotlin.test.assertEquals

internal class MonkeyTest {
    @Test
    fun recordWords() {
        val monitor = object : MonkeyMonitor {
            var keystrokesOnUpdate: Long? = null

            override fun update(monkey: Monkey, words: Words) {
                keystrokesOnUpdate = monkey.totalKeystrokes
                Console.info("monkey", monkey)
                Console.info("monkey.totalKeystrokes", monkey.totalKeystrokes)
                Console.info("words", words)
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

        }
        val corpus = CorpusFactory.dualCorpusOf(ResourceUtil.FULL_FILE)
        val monkey = DualCorpusMonkey(1, corpus)
        monkey.monitors += monitor
        val words1 = listOf("this" to 3, "is" to 17, "a" to 9, "test" to 6)
            .map { Word(it.first, it.second) }
            .let { Words(it, 100, 10) }
        assertEquals(0L, monkey.totalKeystrokes)
        monkey.recordWords(words1)
        assertAll(
            { assertEquals(0L, monitor.keystrokesOnUpdate) },
            { assertEquals(100L, monkey.totalKeystrokes) }
        )
        val words2 = listOf("also" to 4, "this" to 1, "instance" to 9)
            .map { Word(it.first, it.second) }
            .let { Words(it, 42, 10) }
        assertEquals(100L, monkey.totalKeystrokes)
        monkey.recordWords(words2)

    }
}