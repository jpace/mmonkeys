package org.incava.mmonkeys

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.corpus.LengthCorpusMonkey
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MonkeyFactoryTest {
    @Test
    fun createMonkey() {
        val monkeyCtor = ::LengthCorpusMonkey
        val obj = CorpusMonkeyFactory({ Typewriter() }, ctor = monkeyCtor, charsCtor = Keys.fullList())
        val monkey = obj.createMonkey(Corpus(listOf("abc")))
        Console.info("monkey", monkey)
        assertEquals(Typewriter::class.java, monkey.typewriter.javaClass)
    }
}