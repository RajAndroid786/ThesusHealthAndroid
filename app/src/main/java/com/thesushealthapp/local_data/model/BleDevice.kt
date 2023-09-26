package com.thesushealth.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.polidea.rxandroidble3.RxBleConnection
import com.polidea.rxandroidble3.scan.ScanResult
import kotlinx.parcelize.Parcelize


@Entity(tableName = "devices")
@Parcelize
data class BleDevice (
    @PrimaryKey(autoGenerate = true)
    val deviceId: Long = 0,
    var name: String,
    var macAddress: String,
    ) : Parcelable {

    companion object {
        fun getFromScanResult(it: ScanResult): BleDevice {
            return BleDevice(0, it.bleDevice.name ?: "Unknown", it.bleDevice.macAddress)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is BleDevice) return false
        if (this.macAddress == other.macAddress) return true
        return super.equals(other)
    }

    override fun hashCode(): Int {
            return super.hashCode()
    }

}