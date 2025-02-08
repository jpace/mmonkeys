package org.incava.mmonkeys.mky.corpus.sc

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Test

class SequenceFinderTest {
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)

    @Test
    fun presentTwos() {
        val obj = SequenceFinder(words)
        val result = obj.presentTwos
        result.forEach { (a, bs) ->
            bs.forEach { b ->
                Console.info("<<$a$b>>")
            }
        }
    }
}