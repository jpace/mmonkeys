package org.incava.mmonkeys.mky.corpus

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter

typealias CorpusMonkeyCtor<T> = (id: Int, typewriter: Typewriter, sought: T) -> Monkey

class CorpusMonkeyFactory<T : Corpus>(
    val typewriterSupplier: (chars: List<Char>) -> Typewriter = ::Typewriter,
    val monkeyCtor: CorpusMonkeyCtor<T> = ::EqMonkey,
    val charsCtor: List<Char> = Keys.fullList(),
) {
    private var id: Int = 1

    fun createMonkey(corpus: T, id: Int = this.id++): Monkey {
        val typewriter = typewriterSupplier(charsCtor)
        return monkeyCtor(id, typewriter, corpus)
    }
}