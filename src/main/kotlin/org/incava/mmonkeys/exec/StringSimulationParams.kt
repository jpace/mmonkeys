package org.incava.mmonkeys.exec

import org.incava.mmonkeys.MonkeyFactory

class StringSimulationParams(numMonkeys: Int, sought: String, monkeyFactory: MonkeyFactory) :
    SimulationParams<String>(numMonkeys, monkeyFactory, sought)