package org.incava.mmonkeys

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.corpus.CorpusMatcher
import org.incava.mmonkeys.match.corpus.LengthCorpusMatcher
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals

class MonkeyFactoryTest {

    @Test
    fun createMonkey() {
        val corpusMatcher = ::LengthCorpusMatcher
        val obj = MonkeyFactory({ Typewriter() }, corpusMatcher, chars = Keys.fullList())
        val (monkey, matching) = obj.createCorpusMatcher(Corpus(listOf("abc")))
        Console.info("monkey", monkey)
        assertAll(
            { assertEquals(Typewriter::class.java, monkey.typewriter.javaClass) },
            { assertEquals<Class<out CorpusMatcher>>(LengthCorpusMatcher::class.java, obj.corpusMatcher(monkey, Corpus(emptyList())).javaClass) }
        )
    }
}