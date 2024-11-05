package org.incava.mmonkeys.trials.rand

import org.incava.confile.Profiler
import org.incava.confile.SortType

open class RandomStringProfileBase(numInvokes: Long, trialInvokes: Int) {
    val profiler = Profiler(numInvokes, trialInvokes)
    val functions: Map<String, () -> StrSupplier>

    init {
        // we know that list[x] is faster than map[x] where sizes <= 100, so don't always test this:
        val profileMaps = true

        val slotsProviders = mapOf(
            "calc array" to StrRandFactory.calcArray,
            "calc list" to StrRandFactory.calcList,
            "calc map" to StrRandFactory.calcMap,
            "gen array" to StrRandFactory.genArray,
            "gen list" to StrRandFactory.genList,
            "gen map" to StrRandFactory.genMap,
        )

        val stringProviders = mapOf(
            "builder" to StrRandFactory.build,
            "assemble" to StrRandFactory.assemble,
            "buffer" to StrRandFactory.buffer
        )

        functions = slotsProviders.keys
            .filter { profileMaps || !it.contains("map") }
            .flatMap { slotsType ->
                stringProviders.map { (provName, stringProvider) ->
                    "$slotsType $provName" to {
                        StrRandFactory.create(
                            100,
                            slotsProviders.getValue(slotsType),
                            stringProvider
                        )
                    }
                }
            }.toMap()
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
