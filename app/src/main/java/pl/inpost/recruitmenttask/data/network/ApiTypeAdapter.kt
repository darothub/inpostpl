package pl.inpost.recruitmenttask.data.network

import android.os.Build
import androidx.annotation.RequiresApi
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import dagger.Reusable
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
@RequiresApi(Build.VERSION_CODES.O)
@Reusable
class ApiTypeAdapter @Inject constructor() {
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @FromJson
    fun toZonedDateTime(value: String): ZonedDateTime = formatter.parse(value, ZonedDateTime::from)

    @ToJson
    fun fromZonedDateTime(date: ZonedDateTime?): String? = date?.format(formatter)
}
