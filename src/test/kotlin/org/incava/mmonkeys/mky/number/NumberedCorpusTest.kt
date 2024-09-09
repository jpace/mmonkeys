package org.incava.mmonkeys.mky.number

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.testutil.ResourceUtil
import org.junit.jupiter.api.Test
import kotlin.random.Random
import kotlin.test.assertEquals

internal class NumberedCorpusTest {
    @Test
    fun rangeEncoded() {
        val file = ResourceUtil.getResourceFile("pg100.txt")
        val words = CorpusFactory.readFileWords(file, 100).filter { it.length in 1..12 }
        val obj = NumberedCorpus(words)
        Console.info("obj", obj)
        Console.info("obj.rangeEncoded", obj.rangeEncoded)
        val result = obj.rangeEncoded[3]
        assertEquals(702, result?.first)
        assertEquals((702 + 1) * 26, result?.second)
    }

    @Test
    fun findMatch() {
        val rangesEncoded = (1..14).associateWith { length ->
            val encoded = StringEncoderV3.encodeToLong("a".repeat(length))
            encoded to (encoded + 1) * 26
        }

        rangesEncoded.forEach { (length, range) ->
            Console.info("length", length)
            Console.info("from", range.first)
            Console.info("to", range.second)
            Console.info("diff", Long.MAX_VALUE - range.second)
        }

        val length = 3

        val rangeEncoded = rangesEncoded[length] ?: return
        val range = rangeEncoded.first * 25 + 26
        Console.info("range", range)
        Console.info("rangeEncoded", rangeEncoded)
        Console.info("rangeEncoded diff", rangeEncoded.second - rangeEncoded.first)
        val randInRange = Random.nextLong(range)
        Console.info("randInRange", randInRange)
        val encoded = rangeEncoded.first + randInRange
        Console.info("encoded", encoded)

        val decoded = StringEncoderV3.decode(encoded)
        Console.info("decoded", decoded)
    }
}