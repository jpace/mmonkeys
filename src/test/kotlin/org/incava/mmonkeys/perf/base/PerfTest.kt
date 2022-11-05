package org.incava.mmonkeys.perf.base

class PerfTest {
    private val table = PerfTable()

    init {
        table.writeHeader()
    }

    fun addTrial(type: String, perfTrial: PerfTrial<String>, numMatches: Int) {
        val results = perfTrial.run(numMatches)
        table.addResults(type, numMatches, perfTrial.sought.length, results)
    }
}