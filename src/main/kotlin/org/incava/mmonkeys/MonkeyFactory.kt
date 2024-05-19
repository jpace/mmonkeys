package org.incava.mmonkeys

import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.corpus.CorpusMatcher
import org.incava.mmonkeys.match.corpus.LengthCorpusMatcher
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter

class MonkeyFactory(
    val typewriterSupplier: (chars: List<Char>) -> Typewriter = ::Typewriter,
    val charSupplier: () -> List<Char> = { Keys.fullList() },
    val corpusMatcher: (monkey: Monkey, corpus: Corpus) -> CorpusMatcher = ::LengthCorpusMatcher
) {
    private var id: Int = 1

    fun createMonkey(id: Int = this.id++): Monkey {
        val chars = charSupplier()
        val typewriter = typewriterSupplier(chars)
        return Monkey(id, typewriter)
    }

    fun createCorpusMatcher(monkey: Monkey, corpus: Corpus) : CorpusMatcher {
        return corpusMatcher(monkey, corpus)
    }
}
