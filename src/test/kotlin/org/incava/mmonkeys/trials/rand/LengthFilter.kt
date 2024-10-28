package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.mky.corpus.DualCorpus
import org.incava.mmonkeys.mky.corpus.MapCorpus

object LengthFilterFactory {
    fun create(corpus: MapCorpus, length: Int): LengthFilter {
        return LengthFilter(corpus, length)
    }
}

class LengthFilter(private val candidates: Set<String>?, length: Int) : LengthFiltering(length) {
    constructor(corpus: MapCorpus, length: Int) : this(corpus.forLength(length)?.keys, length)
    constructor(corpus: DualCorpus, length: Int) : this(corpus.stringsForLength(length)?.keys, length)

    private var current = ""

    override fun checkLength(): Boolean {
        return candidates != null
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
