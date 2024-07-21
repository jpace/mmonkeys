package org.incava.mmonkeys.mky.number

import org.incava.mmonkeys.trials.corpus.CorpusUtil
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class NumberedCorpusTest {
    @Test
    fun rangeEncoded() {
        val obj = CorpusUtil.toCorpus("pg100.txt", 100, 5, ::NumberedCorpus)
        val result = obj.rangeEncoded[3]
        assertEquals(702, result?.first)
        assertEquals((702 + 1) * 26, result?.second)
    }
}