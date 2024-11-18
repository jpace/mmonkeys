package org.incava.mmonkeys.trials.rand

import org.incava.confile.Profiler
import org.incava.confile.SortType
import org.incava.rando.RandSlotsFactory

open class RandomStringProfile(numInvokes: Long, trialInvokes: Int) {
    private val profiler = Profiler(numInvokes, trialInvokes)

    init {
        val slotsProviders = mapOf(
            "calc array" to RandSlotsFactory::calcArray,
        )

        val stringProviders = mapOf(
            "builder" to StrRandFactory.build,
            "assemble" to StrRandFactory.assemble,
        )

        val functions = slotsProviders.keys
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
    }
}

fun main() {
    val obj = RandomStringProfile(5_000_000L, 3)
    obj.profile()
}