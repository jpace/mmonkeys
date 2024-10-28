package org.incava.mmonkeys.mky.number

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.testutil.ResourceUtil
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class RandEncodedTest {
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE, 100).filter { it.length in 1..12 }

    @Test
    fun random() {
        val result = RandEncoded.random(3) ?: throw AssertionError()
        Console.info("result", result)
        assertTrue(result >= 702, "result: $result")
        assertTrue(result < 18_278, "result: $result")
        Console.info("result", StringEncoder.decode(result))
    }
}