import org.incava.mmonkeys.Console.log
import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.Monkeys
import org.incava.mmonkeys.StandardTypewriter
import org.incava.mmonkeys.Word
import java.lang.Thread.sleep
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    log("main")
    val charList = ('a'..'g').toList() + ' '
    val typewriter = StandardTypewriter(charList)
    val numMonkeys = charList.size
    val soughtString = "abc"
    val soughtWord = Word(soughtString)
    val iterations = 7
    val random = Random.Default
    val wordDurations = mutableListOf<Long>()
    val stringDurations = mutableListOf<Long>()
    for (i in 0 until iterations) {
        if (random.nextBoolean()) {
            log("word -------------------------------------")
            val duration = measureTimeMillis {
                val monkeyList = (0 until numMonkeys).map { Monkey(it, typewriter) }
                val monkeys = Monkeys(monkeyList)
                val resultWord = monkeys.run(soughtWord, 100_000_000L)
                log("resultWord", resultWord)
            }
            log(".. word duration", duration)
            wordDurations.add(duration)
        }
        sleep(10000L)
        if (random.nextBoolean()) {
            log("string -----------------------------------")
            val duration = measureTimeMillis {
                val monkeyList = (0 until numMonkeys).map { Monkey(it, typewriter) }
                val monkeys = Monkeys(monkeyList)
                val resultString = monkeys.run(soughtString, 100_000_000L)
                log("resultString", resultString)
            }
            log(".. string duration", duration)
            stringDurations.add(duration)
        }
        sleep(10000L)
    }
    wordDurations.forEach { log("word", it) }
    log("word.average", wordDurations.average().toLong())
    stringDurations.forEach { log("string", it) }
    log("string.average", stringDurations.average().toLong())
}
