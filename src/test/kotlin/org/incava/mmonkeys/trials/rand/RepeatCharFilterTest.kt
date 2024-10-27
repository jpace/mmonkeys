package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.mmonkeys.trials.corpus.CorpusFilter
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class RepeatCharFilterTest {
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE, -1)
    val filter = CorpusFilter(words)

    @Test
    fun checkValid2() {
        val obj = RepeatCharFilter(filter)
        val r1 = obj.check('c')
        assertTrue(r1)
        val r2 = obj.check('a')
        assertTrue(r2)
    }

    @Test
    fun checkInvalid2() {
        val obj = RepeatCharFilter(filter)
        val r1 = obj.check('c')
        assertTrue(r1)
        val r2 = obj.check('z')
        assertFalse(r2)
    }
}