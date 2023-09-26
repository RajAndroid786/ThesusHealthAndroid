package com.thiagoneves.architecturecomponents.data.repository

import android.bluetooth.BluetoothGattService
import android.util.Log
import androidx.lifecycle.LiveData
import com.thesushealth.data.dao.BleCharacteristicDao
import com.thesushealth.data.dao.BleDeviceDao
import com.thesushealth.data.dao.BleServiceDao
import com.thesushealth.data.model.BleCharacteristic
import com.thesushealth.data.model.BleDevice
import com.thesushealth.data.model.BleService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList


//TODO add any DI framework here
class BLEDeviceRepository(
    private val deviceDao: BleDeviceDao,
    private val serviceDao: BleServiceDao,
    private val characteristicDao: BleCharacteristicDao
) {

    fun getAllData(): LiveData<List<BleDevice>> = deviceDao.getAll()

    fun insertDevice(toDoData: BleDevice) : Long {
        return deviceDao.insertDevice(toDoData)
    }

    suspend fun updateData(toDoData: BleDevice){
        deviceDao.updatedData(toDoData)
    }

    suspend fun deleteData(toDoData: BleDevice){
        deviceDao.deleteData(toDoData)
    }

    suspend fun deleteAll(){
        deviceDao.deleteAll()
    }


    //this method was architected as we need to save the device, service and characteristics
    fun saveDeviceWithServiceAndCharacteristics(device: BleDevice, services: List<BluetoothGattService>) {

            val deviceId = insertDevice(device)
            for (bluetoothGateService in services) {
                val service = BleService(0, deviceId, bluetoothGateService.type, bluetoothGateService.uuid.toString())
                val serviceId = serviceDao.insertService(service)
                for (bluetoothGattCharacteristic in bluetoothGateService.characteristics) {
                    val characteristic = BleCharacteristic(0, serviceId, bluetoothGattCharacteristic.uuid.toString(), bluetoothGattCharacteristic.properties)
                    characteristic.serviceId = serviceId
                    Log.d("BLEDeviceRepository", "insertCharacteristic: ${characteristic.characteristicId}")
                    characteristicDao.insertCharacteristic(characteristic)
                }
            }


    }

}