package org.incava.mmonkeys.mky.string

import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.CorpusMonkeyCtor
import org.incava.mmonkeys.testutil.MonkeyUtils
import kotlin.test.assertEquals

internal open class CorpusMonkeyTestBase {
    fun <T : Corpus> runCheckTest(expected: Long, corpus: T, chars: List<Char>, ctor: CorpusMonkeyCtor<T>) {
        val obj = MonkeyUtils.createMonkey(corpus, ctor, chars)
        val result = MonkeyUtils.runTest(obj, 10L)
        assertEquals(expected, result)
    }
}