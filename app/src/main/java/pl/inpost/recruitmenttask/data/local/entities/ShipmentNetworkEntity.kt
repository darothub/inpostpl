package pl.inpost.recruitmenttask.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import pl.inpost.recruitmenttask.data.local.ShipmentStatusConverter
import pl.inpost.recruitmenttask.data.model.CustomerNetwork
import pl.inpost.recruitmenttask.data.model.OperationsNetwork
import pl.inpost.recruitmenttask.data.model.ShipmentStatus

@Entity(tableName = "shipments")
@TypeConverters(ShipmentStatusConverter::class)
data class ShipmentNetworkEntity(
    @PrimaryKey
    val number: String,
    val shipmentType: String,
    val status: ShipmentStatus,
    val openCode: String?,
    val expiryDate: Long?,
    val storedDate: Long?,
    val pickUpDate: Long?,
    @Embedded(prefix = "receiver_")
    val receiver: CustomerNetwork?,
    @Embedded(prefix = "sender_")
    val sender: CustomerNetwork?,
    @Embedded(prefix = "op_")
    val operations: OperationsNetwork,
    var archived: Boolean = false

)

@Entity(tableName = "event_logs", foreignKeys = [ForeignKey(entity = ShipmentNetworkEntity::class,
    parentColumns = ["number"],
    childColumns = ["shipmentNumber"],
    onDelete = ForeignKey.CASCADE)])
data class EventLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(index = true)
    val shipmentNumber: String,
    val name: String,
    val date: Long
)