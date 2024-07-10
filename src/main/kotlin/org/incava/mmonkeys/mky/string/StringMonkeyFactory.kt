package org.incava.mmonkeys.mky.string

import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter

typealias StringMonkeyCtor = (sought: String, id: Int, typewriter: Typewriter) -> StringMonkey

class StringMonkeyFactory(
    val typewriterSupplier: (chars: List<Char>) -> Typewriter = ::Typewriter,
    val ctor: StringMonkeyCtor = ::EqStringMonkey,
    val charsCtor: List<Char> = Keys.fullList(),
) {
    private var id: Int = 1

    fun createMonkey(sought: String, id: Int = this.id++): StringMonkey {
        val typewriter = typewriterSupplier(charsCtor)
        return ctor(sought, id, typewriter)
    }
}