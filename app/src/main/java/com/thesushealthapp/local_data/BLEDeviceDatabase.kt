package com.thesushealth.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room

import androidx.room.RoomDatabase
import com.thesushealth.data.dao.BleDeviceDao
import com.thesushealth.data.dao.BleCharacteristicDao
import com.thesushealth.data.dao.BleServiceDao
import com.thesushealth.data.model.BleCharacteristic
import com.thesushealth.data.model.BleDevice
import com.thesushealth.data.model.BleDeviceWithServices
import com.thesushealth.data.model.BleDeviceWithServicesAndCharacteristics
import com.thesushealth.data.model.BleService
import com.thesushealth.data.model.BleServiceWithCharacteristics


@Database(entities = [BleDevice::class, BleService::class, BleCharacteristic::class],
    views = [BleDeviceWithServices::class, BleServiceWithCharacteristics::class, BleDeviceWithServicesAndCharacteristics::class], version = 1, exportSchema = false)
abstract class BLEDeviceDatabase : RoomDatabase() {

    abstract fun deviceDao() : BleDeviceDao
    abstract fun bleServiceDao(): BleServiceDao
    abstract fun bleCharacteristicDao(): BleCharacteristicDao


    companion object {

        @Volatile
        private var INSTANCE: BLEDeviceDatabase? = null

        fun getDatabase(context: Context) : BLEDeviceDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BLEDeviceDatabase::class.java,
                    name = "ble").build()
                    INSTANCE = instance
                    return instance
            }
        }
    }

}