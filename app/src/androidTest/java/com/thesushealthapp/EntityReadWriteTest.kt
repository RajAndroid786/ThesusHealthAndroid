package com.thesushealthapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4

import com.thesushealth.data.BLEDeviceDatabase
import com.thesushealth.data.dao.BleCharacteristicDao
import com.thesushealth.data.dao.BleDeviceDao
import com.thesushealth.data.dao.BleServiceDao
import com.thiagoneves.architecturecomponents.data.repository.BLEDeviceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class EntityReadWriteTest {
    private lateinit var bleDeviceDao: BleDeviceDao
    private lateinit var bleServiceDao: BleServiceDao
    private lateinit var characteristicDao: BleCharacteristicDao
    private lateinit var repository: BLEDeviceRepository

    private lateinit var db: BLEDeviceDatabase
    val device1 = TestUtil.createTestDevice1()
    val device2 = TestUtil.createTestDevice2()



    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
                context, BLEDeviceDatabase::class.java)
            .allowMainThreadQueries().build()
        bleDeviceDao = db.deviceDao()
        bleServiceDao = db.bleServiceDao()
        characteristicDao = db.bleCharacteristicDao()
        repository = BLEDeviceRepository(bleDeviceDao, bleServiceDao, characteristicDao)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(IOException::class)
    fun insertDevicesAndGetAllDevices() = runBlocking {
        bleDeviceDao.insertDevice(device1)
        bleDeviceDao.insertDevice(device2)

        val all = bleDeviceDao.getAll().blockingValue

        assert(all!!.contains(device1))
        assert(all[0].deviceId == device1.deviceId)
        assert(all[0].name == device1.name)
        assert(all[0].macAddress == device1.macAddress)

        assert(all.contains(device2))
        assert(all[1].deviceId == device2.deviceId)
        assert(all[1].name == device2.name)
        assert(all[1].macAddress == device2.macAddress)

    }

    @Test
    @Throws(IOException::class)
    fun insertAllDataAndGetServicesAndCharacteristics() {

        repository.saveDeviceWithServiceAndCharacteristics(device1, TestUtil.createMockBluetoothGattServices())

        val devicesWithServicesAndCharacteristics = bleDeviceDao.getDevicesWithServices()

        assert(devicesWithServicesAndCharacteristics.size == 1)

        val deviceWithServices = devicesWithServicesAndCharacteristics[0]

        assert(deviceWithServices.device.deviceId == device1.deviceId)
        assert(deviceWithServices.device.name == device1.name)

        deviceWithServices.servicesAndCharacteristics.forEach() { serviceAndCharacteristic ->
            assert(serviceAndCharacteristic.service.deviceId == device1.deviceId)
            serviceAndCharacteristic.characteristics.forEach() { characteristic ->
                assert(characteristic.serviceId == serviceAndCharacteristic.service.serviceId)
            }
        }

        assert(deviceWithServices.servicesAndCharacteristics.size == TestUtil.createMockBluetoothGattServices().size)

    }

    @Test
    @Throws(IOException::class)
    fun insertAllDataAndGetServicesAndCharacteristics2() {

        repository.saveDeviceWithServiceAndCharacteristics(device2, TestUtil.createMockBluetoothGattServices2())

        val devicesWithServicesAndCharacteristics = bleDeviceDao.getDevicesWithServices()

        val deviceWithServices = devicesWithServicesAndCharacteristics[0]

        assert(deviceWithServices.device.deviceId != device1.deviceId)
        assert(deviceWithServices.device.name != device1.name)

        deviceWithServices.servicesAndCharacteristics.forEach() { serviceAndCharacteristic ->
            assert(serviceAndCharacteristic.service.deviceId != device1.deviceId)
            serviceAndCharacteristic.characteristics.forEach() { characteristic ->
                assert(characteristic.serviceId == serviceAndCharacteristic.service.serviceId)
            }
        }

    }

    @Test
    @Throws(IOException::class)
    fun writeDeviceAndServicesAndReadInList() {
        bleDeviceDao.insertDevice(device1)
        val all = bleDeviceDao.getAll().blockingValue
        assert(all!!.contains(device1))
        assert(all[0].deviceId != device2.deviceId)
    }
}

val <T> LiveData<T>.blockingValue: T?
    get() {
        var value: T? = null
        val latch = CountDownLatch(1)
        GlobalScope.launch(Dispatchers.Main) {
            observeForever {
                value = it
                latch.countDown()
            }
        }

        if (latch.await(2, TimeUnit.SECONDS)) return value
        else throw Exception("LiveData value was not set within 2 seconds")
    }
