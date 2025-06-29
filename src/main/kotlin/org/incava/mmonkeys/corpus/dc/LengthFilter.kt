package org.incava.mmonkeys.corpus.dc

class LengthFilter(private val candidates: Set<String>?) {
    private var current = ""

    fun hasCandidates(): Boolean = candidates != null

    fun check(ch: Char): Boolean {
        return if (candidates == null) {
            false
        } else {
            current += ch
            candidates.any { it.startsWith(current) }
        }
    }
}
