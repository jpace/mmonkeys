package org.incava.mmonkeys.mky

import org.incava.mmonkeys.words.Attempt

interface AttemptObserver {
    fun onSuccess(attempt: Attempt)
    fun onFailed(string: String)
}
