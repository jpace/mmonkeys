package org.incava.mmonkeys.mky.number

import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class NumberedCorpusTest {
    @Test
    fun rangeEncoded() {
        val file = ResourceUtil.getResourceFile("pg100.txt")
        val obj = CorpusFactory.createCorpus(file, 100, 5, ::NumberedCorpus)
        val result = obj.rangeEncoded[3]
        assertEquals(702, result?.first)
        assertEquals((702 + 1) * 26, result?.second)
    }
}