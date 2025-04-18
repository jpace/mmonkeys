package org.incava.mmonkeys.corpus

import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

class CorpusFactoryTest {
    @Test
    fun readFileWords() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE, 100_000)
        assertEquals(602_945, words.size);
        assertFalse(words.contains(""))
    }
}