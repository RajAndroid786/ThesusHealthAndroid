package com.thesushealth.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "services")
data class BleService(
    @PrimaryKey(autoGenerate = true)
    val serviceId: Long = 0,
    var deviceId: Long, // Foreign key to associate with BleDevice
    val serviceType: Int,
    val uuid: String,
)