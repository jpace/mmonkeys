package org.incava.mmonkeys.mky.number

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.testutil.ResourceUtil
import org.junit.jupiter.api.Test
import java.time.ZoneId
import java.time.ZonedDateTime
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

    @Test
    fun dateTime() {
        var dateTime = ZonedDateTime.of(-1, 12, 31, 23, 58, 58, 0, ZoneId.of("UTC"))
        Console.info("dateTime", dateTime)
        repeat(100) {
            dateTime = dateTime.plusSeconds(1)
            Console.info("dateTime", dateTime)
        }
    }
}