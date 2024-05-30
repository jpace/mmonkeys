package org.incava.mmonkeys.exec

import org.incava.mmonkeys.MonkeyFactory

open class SimulationParams<T>(
    val numMonkeys: Int,
    val monkeyFactory: MonkeyFactory,
    val sought: T,
)