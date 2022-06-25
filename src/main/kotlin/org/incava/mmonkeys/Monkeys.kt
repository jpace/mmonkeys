package org.incava.mmonkeys

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.incava.mmonkeys.util.Console.log

class Monkeys(
    private val list: List<Monkey>,
    private val sought: String,
    maxAttempts: Long,
) : BaseMonkeys(maxAttempts) {
    override fun CoroutineScope.launchMonkeys() = list.map { monkey ->
        launch {
            runMonkey(monkey)
        }
    }

    private suspend fun runMonkey(monkey: Monkey) {
        (0 until maxAttempts).forEach { iteration ->
            if (found.get()) {
                return
            } else {
                val result = monkey.nextString()
                iterations.incrementAndGet()
                if (result == sought) {
                    log("success", monkey.id)
                    log("result", result)
                    log("iteration", iteration + 1)
                    found.set(true)
                    return
                }
            }
            delay(5L)
        }
    }
}