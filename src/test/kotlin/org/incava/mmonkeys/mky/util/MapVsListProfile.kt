package org.incava.mmonkeys.mky.util

import org.incava.mmonkeys.trials.base.Profiler
import org.incava.mmonkeys.trials.base.SortType
import java.util.*
import kotlin.random.Random

class MapVsListProfile(private val numInvokes: Long, private val trialInvokes: Int = 5) {
    fun profile() {
        val arrayList = generate(27, 100)
        val linkedHashMap = arrayList.associateByTo(linkedMapOf()) { it }
        val hashMap = arrayList.associateByTo(hashMapOf()) { it }
        val treeMap = arrayList.associateByTo(TreeMap()) { it }

        val profiler = Profiler(numInvokes, trialInvokes)

        profiler.add("array list") {
            val index = Random.Default.nextInt(100)
            arrayList[index]
        }

        profiler.add("linked hash map") {
            val index = Random.Default.nextInt(100)
            linkedHashMap[index]
        }

        profiler.add("hash map") {
            val index = Random.Default.nextInt(100)
            hashMap[index]
        }

        profiler.add("tree map") {
            val index = Random.Default.nextInt(100)
            treeMap[index]
        }

        profiler.runAll()
        profiler.showResults(SortType.BY_INSERTION)
    }

    private fun generate(size: Int, count: Int): List<Int> = (0..count).map {
        (1..10000).find { Random.Default.nextInt(size) == 0 } ?: -1
    }
}

fun main() {
    val obj = MapVsListProfile(100_000_000L, 5)
    obj.profile()
}