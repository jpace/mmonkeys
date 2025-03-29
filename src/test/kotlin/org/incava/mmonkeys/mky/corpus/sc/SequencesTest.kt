package org.incava.mmonkeys.mky.corpus.sc

import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.ZonedDateTime

class SequencesTest {
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)

    @Test
    fun twosChars() {
        val obj = Sequences(words)
        val result = obj.twos
        assertEquals(setOf('u', ' '), result.getValue('q').keys)
        assertEquals(setOf('e', 'o', 'u', 'a', 'i', ' ', 's'), result.getValue('j').keys)
    }

    @Test
    fun twosCounts() {
        val obj = Sequences(words)
        val result = obj.twos
        assertEquals(mapOf('u' to 3941, ' ' to 1), result.getValue('q'))
        assertEquals(mapOf('e' to 1217, 'o' to 1482, 'u' to 1566, 'a' to 535, 'i' to 13, ' ' to 8, 's' to 1), result.getValue('j'))
    }

    @Test
    fun threesChars() {
        val obj = Sequences(words)
        val result = obj.threes
        assertEquals(mapOf('u' to setOf('e', 'i', 'a', 'o', 'y', ' '), ' ' to setOf('u')), result.getValue('q').map { it.key to it.value.keys }.toMap())
        assertEquals(setOf('g', 'l', 'n'), result.getValue('j').getValue('i').keys)
    }

    @Test
    fun threesCounts() {
        val start = ZonedDateTime.now()
        val obj = Sequences(words)
        val result = obj.threes
        assertEquals(mapOf('e' to 1909, 'i' to 1146, 'a' to 626, 'o' to 251, 'y' to 5, ' ' to 4), result.getValue('q').getValue('u'))
        assertEquals(mapOf('g' to 9, 'l' to 3, 'n' to 1), result.getValue('j').getValue('i'))
        val done = ZonedDateTime.now()
        val duration = Duration.between(start, done)
        println("duration: $duration")
    }
}