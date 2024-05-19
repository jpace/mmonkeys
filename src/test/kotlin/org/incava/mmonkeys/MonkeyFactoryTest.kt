package org.incava.mmonkeys

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.match.corpus.LengthCorpusMatcher
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter
import org.junit.jupiter.api.Test

class MonkeyFactoryTest {

    @Test
    fun createMonkey() {
        val typewriter = ::Typewriter
        val charSupplier = { Keys.fullList() }
        val corpusMatcher = ::LengthCorpusMatcher
        val obj = MonkeyFactory(typewriter, charSupplier, corpusMatcher)
        val monkey = obj.createMonkey()
        Console.info("monkey", monkey)
    }

    @Test
    fun createTypewriter() {
    }
}