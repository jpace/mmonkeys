package org.incava.mmonkeys.mky.corpus.sc

import org.incava.mmonkeys.mky.mind.Context
import org.incava.mmonkeys.mky.mind.TypeStrategy
import org.incava.mmonkeys.type.Keys

open class ContextStrategy(val whenEmpty: () -> Char, val withContext: (Context) -> Char) : TypeStrategy() {
    override fun typeWord(): String {
        val builder = StringBuilder()
        val context = Context()
        var ch = whenEmpty()
        while (ch != Keys.END_CHAR) {
            context.currentChars += ch
            builder.append(ch)
            ch = withContext(context)
        }
        return builder.toString()
    }
}