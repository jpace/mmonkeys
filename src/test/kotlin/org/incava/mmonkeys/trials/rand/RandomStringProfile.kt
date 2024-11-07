package org.incava.mmonkeys.trials.rand

import org.incava.confile.Profiler
import org.incava.confile.SortType

open class RandomStringProfile(numInvokes: Long, trialInvokes: Int) {
    val profiler = Profiler(numInvokes, trialInvokes)

    init {
        // we know that list[x] is faster than map[x] where sizes <= 100, so don't always test this:
        val profileMaps = true

        val slotsProviders = mapOf(
            "calc array" to StrRandFactory.calcArray,
            "calc list" to StrRandFactory.calcList,
            "calc map" to StrRandFactory.calcMap,
        )

        val stringProviders = mapOf(
            "builder" to StrRandFactory.build,
            "buffer" to StrRandFactory.buffer
        )

        val functions = slotsProviders.keys
            .filter { profileMaps || !it.contains("map") }
            .flatMap { slotsType ->
                stringProviders.map { (provName, stringProvider) ->
                    "$slotsType $provName" to {
                        StrRandFactory.create(100, slotsProviders.getValue(slotsType), stringProvider)
                    }
                }
            }.toMap()

        functions.forEach { (key, entry) ->
            val gen = entry()
            profiler.add(key) { gen.get() }
        }
    }

    open fun profile() {
        profiler.runAll()
        profiler.showResults(SortType.BY_DURATION)

        val showdown = profiler.spawn()
        showdown.runAll()
        showdown.showResults(SortType.BY_DURATION)

        val showdown2 = showdown.spawn()
        showdown2.runAll()
        showdown2.showResults(SortType.BY_DURATION)
    }
}

fun main() {
    val obj = RandomStringProfile(1_000_000L, 3)
    obj.profile()
}