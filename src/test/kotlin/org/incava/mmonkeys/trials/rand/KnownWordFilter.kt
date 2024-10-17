package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.mky.corpus.MapCorpus

class KnownWordFilter(val corpus: MapCorpus, val length: Int) : GenLenFilter {
    private val candidates = corpus.lengthToStringsToIndices[length]?.keys
    private val current = StringBuilder(length)

    override fun checkLength(length: Int): Boolean {
        return corpus.lengthToStringsToIndices.containsKey(length)
    }

    override fun check(ch: Char): Boolean {
        return if (candidates == null) {
            false
        } else {
            current.append(ch)
            candidates.firstOrNull { it.startsWith(current.toString()) } != null
        }
    }
}
