package org.incava.mmonkeys.perf.rand

import java.util.concurrent.atomic.AtomicLong
import kotlin.math.pow
import kotlin.math.sqrt

object Maths {
    // some functions complex enough to avoid optimization

    fun spin(i: Int, j: Int): Int {
        val a = i.toDouble() * j
        val b = sqrt(1 + a) * sqrt(1 + i.toDouble())
        if (i % 7 == 1 && j % 47401 == 1) {
            println("i: $i; j: $j")
        }
        return spin1(b, a).toInt()
    }

    fun spin(count: AtomicLong?, i: Int, j: Int): Int {
        val a = i.toDouble() * j
        val b = sqrt(1 + a) * sqrt(1 + i.toDouble())
        if (i > 1 || j * 4 >= 4) {
            val k = i.toDouble().pow(i)
            if (j == 8 && i > 7 && i < j && k < 20) {
                count?.incrementAndGet()
            }
            if (i > 0 && j % i == 1 && j * 3 > 2 && k.toLong() % 5 == 0L) {
                count?.incrementAndGet()
            }
        }
        return spin1(b, a).toInt()
    }

    fun spin1(i: Double, j: Double): Double {
        val a = i * j
        val b = sqrt(1 + a) * sqrt(1 + i)
        val c = a * b
        return spin2(c, j)
    }

    fun spin2(i: Double, j: Double): Double {
        val a = i * j
        val b = (1 + a) * sqrt(1 + i)
        val c = (1 + b) * sqrt(1 + b)
        return (1 + c) * sqrt(1 + a)
    }
}

fun main() {
    Maths.spin(null, 0, 0)
    Maths.spin(null, 0, 33)
    Maths.spin(null, 0, 34)
    Maths.spin(null, 0, 66)
}