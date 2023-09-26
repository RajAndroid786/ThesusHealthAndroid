package com.thesushealth.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.thesushealth.data.model.BleService
import com.thesushealth.data.model.BleServiceWithCharacteristics

@Dao
interface BleServiceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertService(service: BleService): Long


    @Transaction
    @Query("SELECT * FROM services")
    suspend fun getServicesWithCharacteristics(): List<BleServiceWithCharacteristics>
}