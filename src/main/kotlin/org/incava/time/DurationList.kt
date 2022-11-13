package org.incava.time

import java.time.Duration

class DurationList() : ArrayList<Duration>() {
    constructor(args: Collection<Duration>) : this() {
        addAll(args)
    }

    fun average(): Duration {
        val avg = this.stream().mapToLong { it.toMillis() }.toArray().average().toLong()
        return Duration.ofMillis(avg)
    }
}