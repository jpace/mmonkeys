package org.incava.mmonkeys.exec

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.MonkeyFactory
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.match.string.StringMatcher

class StringSimulationParams(
    numMonkeys: Int,
    val sought: String,
    matcher: (Monkey, String) -> StringMatcher,
    typewriterFactory: TypewriterFactory = TypewriterFactory(),
) : SimulationParams(numMonkeys, MonkeyFactory({ typewriterFactory.create() }, stringMatcher = matcher)) {
    override fun matcher(monkey: Monkey): Matcher {
        return monkeyFactory.stringMatcher(monkey, sought)
    }
}