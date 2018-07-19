package pl.xierip.xiektapi.extensions

/**
 * Created by xierip on 09.07.18.
 * Web: http://xierip.pl
 */
inline fun Long.formatTimeDiff(): String {
    if (this <= 0) {
        return "1 sekunde"
    }
    var seconds = this / 1000L
    var minutes = seconds / 60L
    var hours = minutes / 60L
    val days = hours / 24L
    seconds -= minutes * 60L
    minutes -= hours * 60L
    hours -= days * 24L
    var time = ""
    if (days != 0L) {
        time = time + days + (if (days == 1L) " dzien " else " dni ")
    }
    if (hours != 0L) {
        time = time + hours + (when (hours) {
            1L -> " godzine "
            2L, 3L, 4L, 22L, 23L, 24L -> " godziny "
            else -> " godzin "
        })
    }
    if (minutes != 0L) {
        time = time + minutes + (when (minutes) {
            1L -> " minute "
            2L, 3L, 4L, 22L, 23L, 24L, 32L, 33L, 34L, 42L, 43L, 44L, 52L, 53L, 54L -> " minuty "
            else -> " minut "
        })
    }
    if (seconds != 0L) {
        time = time + seconds + (when (seconds) {
            1L -> " sekunde "
            2L, 3L, 4L, 22L, 23L, 24L, 32L, 33L, 34L, 42L, 43L, 44L, 52L, 53L, 54L -> " sekundy "
            else -> " sekund "
        })
    }
    if (time == "") {
        return "1 sekunde"
    }
    return time.trim()
}

inline fun Long.formatTimeTo(): String {
    return (this - System.currentTimeMillis()).formatTimeDiff()
}