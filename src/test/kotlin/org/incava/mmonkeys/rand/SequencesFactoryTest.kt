package org.incava.mmonkeys.rand

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Test

class SequencesFactoryTest {

    @Test
    fun createFromWords() {
    }

    @Test
    fun toCharsList() {
        val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE).subList(0, 10000)
        val obj = SequencesFactory.toCharsList(words)
        val matches = mutableSetOf<Triple<Char?, Char, Char?>>()
        obj.indices.forEach {
            if (obj[it] == 'q') {
                val prev = if (it > 0) obj[it - 1] else null
                val next = if (it + 1 < obj.size) obj[it + 1] else null
                val match = Triple(prev, obj[it], next)
                matches += match
                Qlog.info("match", match)
            }
        }
        Qlog.info("matches", matches)
    }
}