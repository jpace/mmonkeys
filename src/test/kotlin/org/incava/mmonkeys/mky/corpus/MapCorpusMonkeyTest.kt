package org.incava.mmonkeys.mky.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.testutil.MonkeyUtils
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test

internal class MapCorpusMonkeyTest {
    private fun makeMonkey(corpus: MapCorpus, chars: List<Char> = Keys.fullList()): CorpusMonkey {
        return MonkeyUtils.createMonkey(corpus, ::MapCorpusMonkey, typewriterCtor = { Typewriter(it) }, chars = chars)
    }

    @Test
    fun check() {
        val obj = makeMonkey(MapCorpus(listOf("ab", "cd", "def", "defg", "ghi")))
        obj as MapCorpusMonkey
        Console.info("obj.class", obj.javaClass)
        Console.info("obj", obj)
        var iterations = 0
        while (!obj.corpus.isEmpty()) {
            val result = obj.check()
            if (result.isMatch) {
                Console.info("obj.corpus", obj.corpus)
                Console.info("obj.corpus.matched", obj.corpus.matched)
            }
            iterations++
        }
        Console.info("iterations", iterations)
        assert(obj.corpus.isEmpty())
    }

    @Test
    fun sharedCorpus() {
        val input = listOf("this", "test", "is", "no", "test")
        val corpus = MapCorpus(input)
        val monkey1 = makeMonkey(corpus) as MapCorpusMonkey
        val monkey2 = makeMonkey(corpus) as MapCorpusMonkey
        var result: MatchData
        do {
            result = monkey1.check()
        } while (!result.isMatch)
        assertSame(monkey1.corpus, monkey2.corpus)
        assertEquals(corpus.lengthToStringsToIndices, corpus.lengthToStringsToIndices)
    }
}