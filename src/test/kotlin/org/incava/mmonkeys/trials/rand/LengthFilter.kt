package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.mky.corpus.MapCorpus

class LengthFilter(val corpus: MapCorpus, length: Int) : LengthFiltering(length) {
    private val candidates = corpus.lengthToStringsToIndices[length]?.keys
    private var current = ""

    override fun checkLength(): Boolean {
        return corpus.lengthToStringsToIndices.containsKey(length)
    }

    override fun check(ch: Char): Boolean {
        return if (candidates == null) {
            false
        } else {
            current += ch
            candidates.firstOrNull { it.startsWith(current) } != null
        }
    }
}
