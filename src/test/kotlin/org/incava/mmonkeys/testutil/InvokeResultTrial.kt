package org.incava.mmonkeys.testutil

open class InvokeResultTrial<T>(block: () -> T) : InvokeTrial<T>(block) {
    val results = mutableListOf<T>()

    override fun runAll(numInvokes: Long) {
        (0 until numInvokes).forEach { _ ->
            val result = block()
            results += result
        }
    }
}