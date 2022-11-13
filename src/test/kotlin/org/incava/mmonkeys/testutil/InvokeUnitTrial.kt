package org.incava.mmonkeys.testutil

open class InvokeUnitTrial<T>(block: () -> T) : InvokeTrial<T>(block) {
    override fun runAll(numInvokes: Long) {
        (0 until numInvokes).forEach { _ ->
            block()
        }
    }
}