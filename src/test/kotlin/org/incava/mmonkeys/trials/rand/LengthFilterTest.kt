package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.MapCorpus
import org.incava.mmonkeys.testutil.ResourceUtil
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class LengthFilterTest {
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
    val corpus = MapCorpus(words)

    @Test
    fun checkLength() {
        val obj1 = LengthFilter(corpus, 5)
        assertTrue { obj1.checkLength() }
        val obj2 = LengthFilter(corpus, 23)
        assertFalse { obj2.checkLength() }
    }

    @Test
    fun checkTrue() {
        val obj1 = LengthFilter(corpus, 3)
        assertTrue { obj1.check('c') }
        assertTrue { obj1.check('a') }
        assertTrue { obj1.check('t') }
    }

    @Test
    fun checkFalse() {
        val obj1 = LengthFilter(corpus, 3)
        assertTrue { obj1.check('c') }
        assertFalse { obj1.check('m') }
    }
}