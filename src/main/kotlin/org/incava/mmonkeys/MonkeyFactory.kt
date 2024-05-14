package org.incava.mmonkeys

import org.incava.mmonkeys.type.Typewriter

class MonkeyFactory(private val typewriterSupplier: () -> Typewriter = { Typewriter() }) {
    private var id: Int = 1

    fun createMonkey(typewriter: Typewriter = typewriterSupplier.invoke(), id: Int = this.id++): Monkey {
        return Monkey(id, typewriter)
    }
}
