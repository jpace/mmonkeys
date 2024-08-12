package org.incava.mmonkeys.mky.number

import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.testutil.ResourceUtil
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class NumberedCorpusTest {
    @Test
    fun rangeEncoded() {
        val file = ResourceUtil.getResourceFile("pg100.txt")
        val words = CorpusFactory.readFileWords(file, 100).filter { it.length > 0 && it.length < 13 }

        val obj = NumberedCorpus(words)
        val result = obj.rangeEncoded[3]
        assertEquals(702, result?.first)
        assertEquals((702 + 1) * 26, result?.second)
    }
}