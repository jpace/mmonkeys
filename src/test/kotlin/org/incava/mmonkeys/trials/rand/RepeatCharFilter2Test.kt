package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.mmonkeys.trials.corpus.CorpusFilter
import kotlin.test.Test

internal class RepeatCharFilter2Test {
    val file = ResourceUtil.getResourceFile("pg100.txt")
    val words = CorpusFactory.readFileWords(file, -1)
    val filter = CorpusFilter(words)

    @Test
    fun checkValid2() {
        val obj = RepeatCharFilter2(filter)
        val r1 = obj.check('c')
        Console.info("r1", r1)

        val r2 = obj.check('a')
        Console.info("r2", r2)
    }

    @Test
    fun checkInvalid2() {
        val obj = RepeatCharFilter2(filter)
        val r1 = obj.check('c')
        Console.info("r1", r1)

        val r2 = obj.check('z')
        Console.info("r2", r2)
    }
}