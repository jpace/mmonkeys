package org.incava.mmonkeys.match.number

import org.incava.mmonkeys.trials.corpus.CorpusUtil
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class NumberedCorpusTest {
    @Test
    fun rangeEncoded() {
        val words = CorpusUtil.readFileWords("pg100.txt", 100, 5)
        val obj = NumberedCorpus(words)
        val result = obj.rangeEncoded[3]
        assertEquals(702, result?.first)
        assertEquals((702 + 1) * 26, result?.second)
    }
}