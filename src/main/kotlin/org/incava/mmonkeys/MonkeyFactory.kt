package org.incava.mmonkeys

import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.corpus.CorpusMonkey
import org.incava.mmonkeys.match.corpus.EqCorpusMonkey
import org.incava.mmonkeys.match.string.EqStringMonkey
import org.incava.mmonkeys.match.string.StringMonkey
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter

class MonkeyFactory(
    val typewriterSupplier: (chars: List<Char>) -> Typewriter = ::Typewriter,
    val corpusMonkeyCtor: (sought: Corpus, id: Int, typewriter: Typewriter) -> CorpusMonkey = ::EqCorpusMonkey,
    val stringMonkeyCtor: (sought: String, id: Int, typewriter: Typewriter) -> StringMonkey = ::EqStringMonkey,
    val charsCtor: List<Char> = Keys.fullList(),
) {
    private var id: Int = 1

    fun createStringMonkey(sought: String, id: Int = this.id++) : StringMonkey {
        val typewriter = typewriterSupplier(charsCtor)
        return stringMonkeyCtor(sought, id, typewriter)
    }

    fun createCorpusMonkey(sought: Corpus, id: Int = this.id++) : CorpusMonkey {
        val typewriter = typewriterSupplier(charsCtor)
        return corpusMonkeyCtor(sought, id, typewriter)
    }
}
