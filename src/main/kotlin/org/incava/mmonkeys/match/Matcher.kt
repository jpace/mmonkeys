package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.rand.RandomFactory
import org.incava.mmonkeys.util.Console
import java.time.ZonedDateTime
import java.util.concurrent.atomic.AtomicLong

abstract class Matcher(val monkey: Monkey) {
    val rand = RandomFactory.getCalculated(monkey.typewriter.numChars())
    var iteration = -1L
    private val counter = AtomicLong()

    abstract fun check(): MatchData

    fun match(keystrokes: Int, index: Int): MatchData {
        return MatchData(true, keystrokes, index)
    }

    fun noMatch(keystrokes: Int): MatchData {
        return MatchData(false, keystrokes, -1)
    }

    fun noMatch(): MatchData {
        val estimated = rand.nextRand()
        return MatchData(false, estimated, -1)
    }

    fun run(maxAttempts: Long = 100_000_000_000_000L): Long {
        iteration = 0L
        while (iteration < maxAttempts) {
            val result = check()
            if (result.isMatch) {
                return iteration
            }
            ++iteration
        }
        println("failing after $iteration iterations")
        throw RuntimeException("failed after $iteration iterations")
    }

    fun tick() {
        if (counter.incrementAndGet() % 100_000_000_000L == 0L) {
            val name = this.javaClass.canonicalName.replace(Regex(".*\\."), "")
            Console.info("$name counter", counter)
            Console.info("$name time", ZonedDateTime.now())
        }
    }

    override fun toString(): String {
        return "Matcher(monkey=$monkey, rand=$rand, iteration=$iteration, counter=$counter)"
    }
}