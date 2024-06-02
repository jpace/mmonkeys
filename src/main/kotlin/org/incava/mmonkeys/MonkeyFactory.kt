package org.incava.mmonkeys

import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.corpus.CorpusMatcher
import org.incava.mmonkeys.match.corpus.EqCorpusMatcher
import org.incava.mmonkeys.match.string.EqStringMatcher
import org.incava.mmonkeys.match.string.EqStringMonkey
import org.incava.mmonkeys.match.string.StringMatcher
import org.incava.mmonkeys.match.string.StringMonkey
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter

class MonkeyFactory(
    val typewriterSupplier: (chars: List<Char>) -> Typewriter = ::Typewriter,
    val corpusMatcher: (monkey: Monkey, corpus: Corpus) -> CorpusMatcher = ::EqCorpusMatcher,
    val stringMatcher: (monkey: Monkey, string: String) -> StringMatcher = ::EqStringMatcher,
    val stringMonkeyCtor: (String, Int, Typewriter) -> StringMonkey = ::EqStringMonkey,
    val chars: List<Char> = Keys.fullList(),
) {
    private var id: Int = 1

    fun createMonkey(id: Int = this.id++): Monkey {
        val typewriter = typewriterSupplier(chars)
        return Monkey(id, typewriter)
    }

    fun createCorpusMatcher(corpus: Corpus) : Pair<Monkey, CorpusMatcher> {
        val typewriter = typewriterSupplier(chars)
        val monkey = Monkey(id, typewriter)
        return monkey to corpusMatcher(monkey, corpus)
    }

    fun createStringMatcher(sought: String) : Pair<Monkey, StringMatcher> {
        val typewriter = typewriterSupplier(chars)
        val monkey = Monkey(id, typewriter)
        return monkey to stringMatcher(monkey, sought)
    }

    fun createStringMonkey(sought: String, id: Int = this.id++) : StringMonkey {
        val typewriter = typewriterSupplier(chars)
        return stringMonkeyCtor(sought, id, typewriter)
    }
}
