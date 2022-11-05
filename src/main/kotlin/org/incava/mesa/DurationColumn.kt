package org.incava.mesa

import java.time.Duration

class DurationColumn(header: String, width: Int) : StringColumn(header, width, leftJustified = false) {
    override fun formatCell(value: Any): String {
        val duration = value as Duration
        val seconds = duration.seconds
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        val millis = duration.nano / 1_000_000
        val str = when {
            hours > 0 -> {
                "%d:%02d:%02d.%03d".format(hours, minutes, secs, millis)
            }
            minutes > 0 -> {
                "%d:%02d.%03d".format(minutes, secs, millis)
            }
            else -> {
                "%d.%03d".format(secs, millis)
            }
        }
        return cellFormat().format(str)
    }
}