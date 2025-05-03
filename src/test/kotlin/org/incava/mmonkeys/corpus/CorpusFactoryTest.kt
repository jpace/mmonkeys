package org.incava.mmonkeys.corpus

import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

class CorpusFactoryTest {
    @Test
    fun readFileWords() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
        assertEquals(987_400, words.size);
        assertFalse(words.contains(""))
    }
}