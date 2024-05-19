package org.incava.mmonkeys

import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.corpus.CorpusMatcher
import org.incava.mmonkeys.match.corpus.EqCorpusMatcher
import org.incava.mmonkeys.match.string.EqStringMatcher
import org.incava.mmonkeys.match.string.StringMatcher
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter

class MonkeyFactory(
    val typewriterSupplier: (chars: List<Char>) -> Typewriter = ::Typewriter,
    val corpusMatcher: (monkey: Monkey, corpus: Corpus) -> CorpusMatcher = ::EqCorpusMatcher,
    val stringMatcher: (monkey: Monkey, string: String) -> StringMatcher = ::EqStringMatcher,
    val chars: List<Char> = Keys.fullList(),
) {
    private var id: Int = 1

    fun createMonkey(id: Int = this.id++): Monkey {
        val typewriter = typewriterSupplier(chars)
        return Monkey(id, typewriter)
    }

    fun createCorpusMatcher(monkey: Monkey, corpus: Corpus): CorpusMatcher {
        return corpusMatcher(monkey, corpus)
    }

    fun createStringMatcher(monkey: Monkey, string: String): StringMatcher {
        return stringMatcher(monkey, string)
    }
}
