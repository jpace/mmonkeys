package org.incava.mmonkeys.mky

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.CorpusMonkeyFactory
import org.incava.mmonkeys.mky.corpus.MapCorpus
import org.incava.mmonkeys.mky.corpus.MapMonkey
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MonkeyFactoryTest {
    @Test
    fun createMonkey() {
        val monkeyCtor = ::MapMonkey
        val obj = CorpusMonkeyFactory({ Typewriter() }, monkeyCtor = monkeyCtor, charsCtor = Keys.fullList())
        val monkey = obj.createMonkey(MapCorpus(listOf("abc")))
        Console.info("monkey", monkey)
        assertEquals(Typewriter::class.java, monkey.typewriter.javaClass)
    }
}