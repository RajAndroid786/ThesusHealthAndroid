package com.thesushealth.data.model

import androidx.room.DatabaseView
import androidx.room.Embedded
import androidx.room.Relation

@DatabaseView(
    """
    SELECT * FROM devices
    INNER JOIN services ON devices.deviceId = services.deviceId
"""
)
data class BleDeviceWithServices(
    @Embedded val device: BleDevice,
    @Relation(
        parentColumn = "deviceId",
        entityColumn = "deviceId"
    )
    val servicesAndCharacteristics: List<BleServiceWithCharacteristics>
)