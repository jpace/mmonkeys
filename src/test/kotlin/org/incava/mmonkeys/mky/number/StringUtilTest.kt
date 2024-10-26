package org.incava.mmonkeys.mky.number

import org.incava.mmonkeys.testutil.StringUtil
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test


internal class StringUtilTest {
    @Test
    fun succ() {
        val str = "zzz"
        val result = StringUtil.succ(str)
        assertEquals("aaaa", result)
    }

    @Test
    fun succOffset() {
        val str = "aaa"
        val result = StringUtil.succ(str, 25)
        assertEquals("aaz", result)
    }
}
