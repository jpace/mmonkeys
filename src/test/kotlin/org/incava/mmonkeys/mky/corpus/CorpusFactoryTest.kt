package org.incava.mmonkeys.mky.corpus

import org.incava.mmonkeys.testutil.ResourceUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

class CorpusFactoryTest {
    @Test
    fun readFileWords() {
        val file = ResourceUtil.getResourceFile("pg100.txt")
        val words = CorpusFactory.readFileWords(file, 100000)
        println(words.filter { it.length == 1}.toSortedSet())
        assertEquals(584_922, words.size);
        assertFalse(words.contains(""))
    }
}