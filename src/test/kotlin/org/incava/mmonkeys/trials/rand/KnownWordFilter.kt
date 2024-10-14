package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.mky.corpus.MapCorpus

class KnownWordFilter(val corpus: MapCorpus, val length: Int) : GenFilter {
    val candidates = corpus.lengthToStringsToIndices[length]?.keys
    val current = StringBuilder(length)

    override fun check(ch: Char): Boolean {
        if (candidates == null) {
            return false
        } else {
            current.append(ch)
            val matching = candidates.find { it.startsWith(current.toString()) }
            return matching != null
        }
    }
}
