package pl.inpost.recruitmenttask.data.model

import android.os.Build
import androidx.annotation.RequiresApi
import pl.inpost.recruitmenttask.data.local.entities.ShipmentNetworkEntity
import java.lang.Exception
import java.time.ZonedDateTime

data class ShipmentNetwork (
    val number: String,
    val shipmentType: String,
    val status: String,
    val eventLog: List<EventLogNetwork>,
    val openCode: String?,
    val expiryDate: ZonedDateTime?,
    val storedDate: ZonedDateTime?,
    val pickUpDate: ZonedDateTime?,
    val receiver: CustomerNetwork?,
    val sender: CustomerNetwork?,
    val operations: OperationsNetwork
)

@RequiresApi(Build.VERSION_CODES.O)
fun ShipmentNetwork.toEntity(): ShipmentNetworkEntity {
    val status: ShipmentStatus = try {
        ShipmentStatus.valueOf(this.status)
    } catch (e: IllegalArgumentException) {
        ShipmentStatus.OTHER
    }
    return ShipmentNetworkEntity(
        number = this.number,
        shipmentType = this.shipmentType,
        status = status,
        openCode = this.openCode,
        expiryDate = this.expiryDate?.toInstant()?.toEpochMilli(),
        storedDate = this.storedDate?.toInstant()?.toEpochMilli(),
        pickUpDate = this.pickUpDate?.toInstant()?.toEpochMilli(),
        receiver = this.receiver,
        sender = this.sender,
        operations = this.operations
    )
}
