package org.incava.mmonkeys.exec

import kotlin.random.Random

class WordVsStringSimulation(val params: SimulationParams) {
    fun run() {
        val str = StringSimulation(params)
        val word = WordSimulation(params)
        repeat(10) {
            val b = Random.Default.nextBoolean()
            if (b) {
                str.run()
            } else {
                word.run()
            }
        }
    }
}