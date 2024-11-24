package org.incava.mmonkeys.mky.corpus

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter

typealias MonkeyCtor<T> = (id: Int, typewriter: Typewriter, sought: T) -> Monkey

class MonkeyFactory<T : Corpus>(val typewriterSupplier: (chars: List<Char>) -> Typewriter, val monkeyCtor: MonkeyCtor<T>) {
    private var id: Int = 1
    val chars: List<Char> = Keys.fullList()

    fun createMonkey(corpus: T, id: Int = this.id++): Monkey {
        val typewriter = typewriterSupplier(chars)
        return monkeyCtor(id, typewriter, corpus)
    }
}