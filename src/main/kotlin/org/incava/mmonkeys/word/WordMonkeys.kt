package org.incava.mmonkeys.word

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.incava.mmonkeys.BaseMonkeys
import org.incava.mmonkeys.util.Console.log

class WordMonkeys(private val list: List<WordMonkey>, private val sought: Word, maxAttempts: Long) : BaseMonkeys(maxAttempts) {
    override fun CoroutineScope.launchMonkeys() = list.map { monkey ->
        launch {
            runMonkey(monkey)
        }
    }

    private suspend fun runMonkey(monkey: WordMonkey) {
        (0 until maxAttempts).forEach { iteration ->
            if (found.get()) {
                return
            } else {
                val result = monkey.nextWord()
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