package org.incava.mmonkeys.trials.rand

import org.incava.confile.Profiler
import org.incava.confile.SortType
import org.incava.ikdk.io.Console
import org.incava.mmonkeys.type.Chars
import org.incava.rando.RandSlotsFactory

open class RandomStringProfileBase(numInvokes: Long, trialInvokes: Int) {
    val profiler = Profiler(numInvokes, trialInvokes)
    val functions: Map<String, () -> StrSupplier>

    init {
        // we know that list[x] is faster than map[x] where sizes <= 100, so don't always test this:
        val profileMaps = false

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

        val keys = slotsProviders.keys.filter { profileMaps || !it.contains("map") }

        val functions1 = keys.flatMap { slotsType ->
            stringProviders.map { (provName, stringProvider) ->
                "$slotsType $provName" to { StrRandFactory.create(100, slotsProviders.getValue(slotsType), stringProvider) }
            }
        }.toMap()

        val calcList = RandSlotsFactory.calcList(Chars.NUM_ALL_CHARS, 100, 10000)
        val calcArray = RandSlotsFactory.calcArray(Chars.NUM_ALL_CHARS, 100, 10000)

        functions = mapOf(
            "calc (array) decode" to { StrRandDecoded(calcArray) },
            "calc (list) decode" to { StrRandDecoded(calcList) },
            "calc (array) encoded" to { StrRandEncoded(calcArray) },
            "calc (list) encoded" to { StrRandEncoded(calcList) },
            "calc big decode (only)" to { StrCalcBigIntOnly(calcList) },
            "calc big decode (toggle)" to { StrCalcBigIntToggle(calcList) },
            "toggle string (array)" to { StrToggleStringRand(calcArray) },
            "toggle string (list)" to { StrToggleStringRand(calcList) },
            "toggle any (array)" to { StrToggleAnyRand(calcArray) },
            "toggle any (list)" to { StrToggleAnyRand(calcList) },
        ) + functions1
    }

    fun addFilters(name: String, generator: StrRand) {
        // val filters = listOf(5, 10, 20)
        val filters = listOf(5, 10, 15, 25)
        val lettersToCount = mapOf(
            1 to 22,
            2 to 130,
            3 to 681,
            4 to 2087,
            5 to 3379,
            6 to 4446,
            7 to 4661,
            8 to 3920,
            9 to 2789,
            10 to 1773,
            11 to 992,
            12 to 440,
            13 to 202,
            14 to 47,
            15 to 27,
            16 to 2,
            17 to 3,
            27 to 1,
        )
        profiler.add(name) { generator.get() }
        filters.forEach { num -> profiler.add("$name $num") { generator.get(num) } }
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
