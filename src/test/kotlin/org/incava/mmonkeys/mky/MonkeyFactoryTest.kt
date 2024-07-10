package org.incava.mmonkeys.mky

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.CorpusMonkeyFactory
import org.incava.mmonkeys.mky.corpus.LengthCorpus
import org.incava.mmonkeys.mky.corpus.LengthCorpusMonkey
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MonkeyFactoryTest {
    @Test
    fun createMonkey() {
        val monkeyCtor = ::LengthCorpusMonkey
        val obj = CorpusMonkeyFactory({ Typewriter() }, ctor = monkeyCtor, charsCtor = Keys.fullList())
        val monkey = obj.createMonkey(LengthCorpus(listOf("abc")))
        Console.info("monkey", monkey)
        assertEquals(Typewriter::class.java, monkey.typewriter.javaClass)
    }
}