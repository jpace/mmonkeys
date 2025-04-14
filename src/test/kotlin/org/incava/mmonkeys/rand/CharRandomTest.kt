package org.incava.mmonkeys.rand

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Test

class CharRandomTest {
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE).subList(0, 100)

    @Test
    fun mapping() {
        val chars = words.flatMap { word -> word.map { char -> char } + ' ' }
        val counted = mutableMapOf<Char, Int>()
        chars.forEach { counted[it] = (counted[it] ?: 0) + 1 }
        val obj = CharRandom.toDistributedRandom(counted)
        Qlog.info("obj", obj)
        Qlog.info("obj.nextRandom()", obj.nextRandom())
    }
}