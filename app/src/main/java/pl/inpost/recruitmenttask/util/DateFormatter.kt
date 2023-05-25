package pl.inpost.recruitmenttask.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
fun Long.toInPostDateString(): String {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy | HH:mm");
    val instant = Instant.ofEpochMilli(this)
    val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    val str = formatter.format(date)
    return "pn. | $str"
}