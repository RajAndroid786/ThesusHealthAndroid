package com.thesushealthapp

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import com.thesushealth.data.model.BleDevice
import java.util.UUID

class TestUtil {
    companion object {
        fun createTestDevice1(): BleDevice {
            return BleDevice(1, "Device 1", "00:11:22:33:44:55")
        }

        fun createTestDevice2(): BleDevice {
            return BleDevice(2, "Device 2", "AA:BB:CC:DD:EE:FF")
        }


        fun createMockBluetoothGattServices(): List<BluetoothGattService> {
            val services = mutableListOf<BluetoothGattService>()

            // Mock service 1
            val service1 = BluetoothGattService(
                UUID.fromString("0000180D-0000-1000-8000-00805F9B34FB"),  // Mock service UUID
                BluetoothGattService.SERVICE_TYPE_PRIMARY
            )
            val characteristic1 = BluetoothGattCharacteristic(
                UUID.fromString("00002A37-0000-1000-8000-00805F9B34FB"),  // Mock characteristic UUID
                BluetoothGattCharacteristic.PROPERTY_READ or BluetoothGattCharacteristic.PROPERTY_NOTIFY,
                BluetoothGattCharacteristic.PERMISSION_READ
            )
            service1.addCharacteristic(characteristic1)
            services.add(service1)

            // Mock service 2
            val service2 = BluetoothGattService(
                UUID.fromString("0000180F-0000-1000-8000-00805F9B34FB"),  // Mock service UUID
                BluetoothGattService.SERVICE_TYPE_PRIMARY
            )
            val characteristic2 = BluetoothGattCharacteristic(
                UUID.fromString("00002A19-0000-1000-8000-00805F9B34FB"),  // Mock characteristic UUID
                BluetoothGattCharacteristic.PROPERTY_READ,
                BluetoothGattCharacteristic.PERMISSION_READ
            )
            service2.addCharacteristic(characteristic2)
            services.add(service2)

            // Mock service 3
            val service3 = BluetoothGattService(
                UUID.fromString("0000180A-0000-1000-8000-00805F9B34FB"),  // Mock service UUID
                BluetoothGattService.SERVICE_TYPE_PRIMARY
            )
            val characteristic3 = BluetoothGattCharacteristic(
                UUID.fromString("00002A29-0000-1000-8000-00805F9B34FB"),  // Mock characteristic UUID
                BluetoothGattCharacteristic.PROPERTY_READ or BluetoothGattCharacteristic.PROPERTY_WRITE,
                BluetoothGattCharacteristic.PERMISSION_READ or BluetoothGattCharacteristic.PERMISSION_WRITE
            )
            service3.addCharacteristic(characteristic3)
            services.add(service3)

            return services
        }


        fun createMockBluetoothGattServices2(): List<BluetoothGattService> {
            val services = mutableListOf<BluetoothGattService>()

            // Mock service 1
            val service1 = BluetoothGattService(
                UUID.fromString("0000180D-0000-1000-8000-20805F9B34FB"),  // Mock service UUID
                BluetoothGattService.SERVICE_TYPE_PRIMARY
            )
            val characteristic1 = BluetoothGattCharacteristic(
                UUID.fromString("00002A37-0000-1000-8000-20805F9B34FB"),  // Mock characteristic UUID
                BluetoothGattCharacteristic.PROPERTY_READ or BluetoothGattCharacteristic.PROPERTY_NOTIFY,
                BluetoothGattCharacteristic.PERMISSION_READ
            )
            service1.addCharacteristic(characteristic1)
            services.add(service1)

            // Mock service 2
            val service2 = BluetoothGattService(
                UUID.fromString("0000180F-0000-1000-8000-20805F9B34FB"),  // Mock service UUID
                BluetoothGattService.SERVICE_TYPE_PRIMARY
            )
            val characteristic2 = BluetoothGattCharacteristic(
                UUID.fromString("00002A19-0000-1000-8000-20805F9B34FB"),  // Mock characteristic UUID
                BluetoothGattCharacteristic.PROPERTY_READ,
                BluetoothGattCharacteristic.PERMISSION_READ
            )
            service2.addCharacteristic(characteristic2)
            services.add(service2)

            // Mock service 3
            val service3 = BluetoothGattService(
                UUID.fromString("0000180A-0000-1000-8000-20805F9B34FB"),  // Mock service UUID
                BluetoothGattService.SERVICE_TYPE_PRIMARY
            )
            val characteristic3 = BluetoothGattCharacteristic(
                UUID.fromString("00002A29-0000-1000-8000-20805F9B34FB"),  // Mock characteristic UUID
                BluetoothGattCharacteristic.PROPERTY_READ or BluetoothGattCharacteristic.PROPERTY_WRITE,
                BluetoothGattCharacteristic.PERMISSION_READ or BluetoothGattCharacteristic.PERMISSION_WRITE
            )
            service3.addCharacteristic(characteristic3)
            services.add(service3)

            return services
        }
    }
}