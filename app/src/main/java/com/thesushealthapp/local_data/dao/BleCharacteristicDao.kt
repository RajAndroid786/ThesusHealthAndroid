package com.thesushealth.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thesushealth.data.model.BleCharacteristic

@Dao
interface BleCharacteristicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacteristic(characteristic: BleCharacteristic): Long

    @Query("SELECT * FROM characteristics")
    suspend fun getAllCharacteristics(): List<BleCharacteristic>
}