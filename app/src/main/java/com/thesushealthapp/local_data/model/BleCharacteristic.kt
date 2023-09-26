package com.thesushealth.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characteristics")
data class BleCharacteristic(
    @PrimaryKey(autoGenerate = true)
    val characteristicId: Long = 0,
    var serviceId: Long, // Foreign key to associate with BleService
    val uuid: String,
    val property: Int
)