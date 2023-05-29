package pl.inpost.recruitmenttask.data.local

import androidx.room.TypeConverter
import dagger.Reusable
import pl.inpost.recruitmenttask.data.model.ShipmentStatus
@Reusable
class ShipmentStatusConverter {
    @TypeConverter
    fun fromStatus(status: ShipmentStatus): Int {
        return status.ordinal
    }
    @TypeConverter
    fun toStatus(ordinal: Int): ShipmentStatus {
        return ShipmentStatus.values().getOrElse(ordinal) {ShipmentStatus.OTHER}
    }
}




