package org.incava.mmonkeys

import org.incava.mmonkeys.match.MatcherMonkey
import org.incava.mmonkeys.match.Matching
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.corpus.CorpusMatcher
import org.incava.mmonkeys.match.corpus.EqCorpusMatcher
import org.incava.mmonkeys.match.string.EqStringMatcher
import org.incava.mmonkeys.match.string.EqStringMonkey
import org.incava.mmonkeys.match.string.PartialStringMonkey
import org.incava.mmonkeys.match.string.StringMatcher
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter

class MonkeyFactory(
    val typewriterSupplier: (chars: List<Char>) -> Typewriter = ::Typewriter,
    val corpusMatcher: (monkey: Monkey, corpus: Corpus) -> CorpusMatcher = ::EqCorpusMatcher,
    val stringMatcher: (monkey: Monkey, string: String) -> StringMatcher = ::EqStringMatcher,
    val stringMatchingCtor: (sought: String, id: Int, typewriter: Typewriter) -> Matching = ::EqStringMonkey,
    val stringMonkeyCtor: (String, Int, Typewriter) -> Monkey = ::EqStringMonkey,
    val chars: List<Char> = Keys.fullList(),
) {
    private var id: Int = 1

    fun createMonkey(id: Int = this.id++): Monkey {
        val typewriter = typewriterSupplier(chars)
        return Monkey(id, typewriter)
    }

    fun createMatchingMonkey(sought: String, id: Int = this.id++): Monkey {
        val typewriter = typewriterSupplier(chars)
        val matching = stringMatchingCtor(sought, id, typewriter)
        return MatcherMonkey(matching, id, typewriter)
    }

    fun createPartialStringMonkey(sought: String, id: Int = this.id++): PartialStringMonkey {
        val typewriter = typewriterSupplier(chars)
        return PartialStringMonkey(sought, id, typewriter)
    }

    fun createCorpusMatcher(monkey: Monkey, corpus: Corpus): CorpusMatcher {
        return corpusMatcher(monkey, corpus)
    }

    fun createCorpusMatcher(corpus: Corpus) : Pair<Monkey, CorpusMatcher> {
        val monkey = createMonkey()
        return monkey to corpusMatcher(monkey, corpus)
    }

    fun createStringMatcher(sought: String) : Pair<Monkey, StringMatcher> {
        val monkey = createMonkey()
        return monkey to stringMatcher(monkey, sought)
    }

    fun createStringMatcher(monkey: Monkey, string: String): StringMatcher {
        return stringMatcher(monkey, string)
    }

    fun <T> createMatcher(monkey: Monkey, sought: T, ctor: (Monkey, T) -> Matching): Matching {
        return ctor(monkey, sought)
    }
}
