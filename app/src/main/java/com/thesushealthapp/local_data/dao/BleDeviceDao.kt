package com.thesushealth.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.thesushealth.data.model.BleDevice
import com.thesushealth.data.model.BleDeviceWithServices
import com.thesushealth.data.model.BleDeviceWithServicesAndCharacteristics


@Dao
interface BleDeviceDao {

    @Query("SELECT * FROM devices ORDER BY deviceId ASC")
    fun getAll(): LiveData<List<BleDevice>>

    @Transaction
    @Query("SELECT * FROM devices")
    fun getDevicesWithServices(): List<BleDeviceWithServicesAndCharacteristics>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDevice(data: BleDevice) : Long

    @Update
    suspend fun updatedData(data: BleDevice)

    @Delete
    suspend fun deleteData(data: BleDevice)

    @Query("DELETE FROM devices")
    suspend fun deleteAll()


}