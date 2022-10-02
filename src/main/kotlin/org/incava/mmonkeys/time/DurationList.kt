package org.incava.mmonkeys.time

import java.time.Duration

class DurationList() : ArrayList<Duration>() {
    constructor(vararg ary: Duration) : this(ary.toList())

    constructor(args: Collection<Duration>) : this() {
        addAll(args)
    }

    fun average(): Duration {
        val avg = this.stream().mapToLong { it.toMillis() }.toArray().average().toLong()
        return Duration.ofMillis(avg)
    }
}