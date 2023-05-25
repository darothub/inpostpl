package pl.inpost.recruitmenttask.data.model

import androidx.annotation.StringRes
import pl.inpost.recruitmenttask.R

/**
 * Order of statuses
 * 1. CREATED
 * 2. CONFIRMED
 * 3. ADOPTED_AT_SOURCE_BRANCH
 * 4. SENT_FROM_SOURCE_BRANCH
 * 5. ADOPTED_AT_SORTING_CENTER
 * 6. SENT_FROM_SORTING_CENTER
 * 7. OTHER
 * 8. DELIVERED
 * 9. RETURNED_TO_SENDER
 * 10. AVIZO
 * 11. OUT_FOR_DELIVERY
 * 12. READY_TO_PICKUP
 * 13. PICKUP_TIME_EXPIRED
 */
enum class ShipmentStatus(
    @StringRes val nameRes: Int
) {
    ADOPTED_AT_SORTING_CENTER(R.string.status_adopted_at_sorting_center) {
        override fun getOrderId() = 5
    },
    SENT_FROM_SORTING_CENTER(R.string.status_sent_from_sorting_center) {
        override fun getOrderId() = 6
    },
    ADOPTED_AT_SOURCE_BRANCH(R.string.status_adopted_at_source_branch) {
        override fun getOrderId() = 3
    },
    SENT_FROM_SOURCE_BRANCH(R.string.status_sent_from_source_branch) {
        override fun getOrderId() = 4
    },
    AVIZO(R.string.status_avizo) {
        override fun getOrderId() = 10
    },
    CONFIRMED(R.string.status_confirmed) {
        override fun getOrderId() = 2
    },
    CREATED(R.string.status_created) {
        override fun getOrderId() = 1
    },
    DELIVERED(R.string.status_delivered) {
        override fun getOrderId() = 8
    },
    OTHER(R.string.status_other) {
        override fun getOrderId() = 7
    },
    OUT_FOR_DELIVERY(R.string.status_out_for_delivery) {
        override fun getOrderId() = 11
    },
    PICKUP_TIME_EXPIRED(R.string.status_pickup_time_expired) {
        override fun getOrderId() = 13
    },
    READY_TO_PICKUP(R.string.status_ready_to_pickup) {
        override fun getOrderId() = 12
    },
    RETURNED_TO_SENDER(R.string.status_returned_to_sender) {
        override fun getOrderId() = 9
    };

    abstract fun getOrderId(): Int
}
//fun main() {
//    ShipmentStatus.valueOf("")
//}