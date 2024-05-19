package org.incava.mmonkeys.exec

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.MonkeyFactory
import org.incava.mmonkeys.match.Matcher

class StringSimulationParams(
    numMonkeys: Int,
    val sought: String,
    monkeyFactory: MonkeyFactory,
) : SimulationParams(numMonkeys, monkeyFactory) {
    override fun matcher(monkey: Monkey): Matcher {
        return monkeyFactory.stringMatcher(monkey, sought)
    }
}