package com.thesushealth.data.model

import androidx.room.DatabaseView
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

@DatabaseView(
    """
    SELECT * 
    FROM devices
    INNER JOIN services ON devices.deviceId = services.deviceId

    """
)
data class BleDeviceWithServicesAndCharacteristics(
    @Embedded val device: BleDevice,
    @Relation(
        parentColumn = "deviceId",
        entityColumn = "deviceId",
        associateBy = Junction(BleService::class,
        parentColumn = "serviceId",
        entityColumn = "serviceId"),
    )
    val servicesAndCharacteristics: List<BleServiceWithCharacteristics>,
)
