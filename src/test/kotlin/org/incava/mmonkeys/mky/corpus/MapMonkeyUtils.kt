package org.incava.mmonkeys.mky.corpus

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.type.Typewriter

object MapMonkeyUtils {
    fun createDefaultMonkey(words: List<String>): Monkey {
        return createMapMonkey(1, MapCorpus(words))
    }

    fun createMapMonkey(id: Int, corpus: MapCorpus): Monkey {
        val typewriter = Typewriter()
        return MapMonkey(id, typewriter, corpus)
    }
}