package org.incava.mmonkeys

import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.corpus.CorpusMonkey
import org.incava.mmonkeys.match.corpus.EqCorpusMonkey
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter

typealias CorpusMonkeyCtor<T> = (sought: T, id: Int, typewriter: Typewriter) -> CorpusMonkey

class CorpusMonkeyFactory<T : Corpus>(
    val typewriterSupplier: (chars: List<Char>) -> Typewriter = ::Typewriter,
    val ctor: CorpusMonkeyCtor<T> = ::EqCorpusMonkey,
    val charsCtor: List<Char> = Keys.fullList(),
) {
    private var id: Int = 1

    fun createMonkey(sought: T, id: Int = this.id++): CorpusMonkey {
        val typewriter = typewriterSupplier(charsCtor)
        return ctor(sought, id, typewriter)
    }
}
