package com.thesushealth.data.model


import androidx.room.DatabaseView
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

@DatabaseView(
    """
    SELECT * FROM services
    INNER JOIN characteristics ON services.serviceId = characteristics.serviceId
"""
)
data class BleServiceWithCharacteristics(
    @Embedded val service: BleService,
    @Relation(
        parentColumn = "serviceId",
        entityColumn = "serviceId",

    )
    val characteristics: List<BleCharacteristic>
)