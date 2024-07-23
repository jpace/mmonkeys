package org.incava.mmonkeys.mky.corpus

import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter

typealias CorpusMonkeyCtor<T> = (sought: T, id: Int, typewriter: Typewriter) -> CorpusMonkey

class CorpusMonkeyFactory<T : Corpus>(
    val typewriterSupplier: (chars: List<Char>) -> Typewriter = ::Typewriter,
    val monkeyCtor: CorpusMonkeyCtor<T> = ::EqCorpusMonkey,
    val charsCtor: List<Char> = Keys.fullList(),
) {
    private var id: Int = 1

    fun createMonkey(corpus: T, id: Int = this.id++): CorpusMonkey {
        val typewriter = typewriterSupplier(charsCtor)
        return monkeyCtor(corpus, id, typewriter)
    }
}