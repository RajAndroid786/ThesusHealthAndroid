package com.thesushealthapp.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.polidea.rxandroidble3.RxBleClient
import com.polidea.rxandroidble3.exceptions.BleScanException
import com.polidea.rxandroidble3.scan.ScanFilter
import com.polidea.rxandroidble3.scan.ScanResult
import com.polidea.rxandroidble3.scan.ScanSettings
import com.thesushealth.data.model.BleDevice
import com.thesushealthapp.AppApplication
import com.thesushealthapp.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow



class ScanViewModel : ViewModel()  {

    private val TAG = "#ScanViewModel"

    private val _deviceList: MutableStateFlow<List<BleDevice>> =
        MutableStateFlow(listOf<BleDevice>())
    val devicesList = _deviceList.asStateFlow()

    private val _rxBleClientState: MutableStateFlow<RxBleClient.State> =
        MutableStateFlow(RxBleClient.State.BLUETOOTH_NOT_AVAILABLE)
    val rxBleClientState = _rxBleClientState.asStateFlow()

    private val rxBleClient = AppApplication.rxBleClient
    private var scanDisposable: Disposable? = null

    private val _isScanning: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isScanning = _isScanning.asStateFlow()


    init {
        _rxBleClientState.value = rxBleClient.state
    }

    private fun scanBLEDevices() {
        Log.d(TAG, "startScan: ")
        if (_isScanning.value) {
            Log.d(TAG, "startScan: _isScanning.value == true return")
            return
        }


        if (_rxBleClientState.value != RxBleClient.State.READY) {
            return
        }

        _isScanning.value = true

        Log.d(TAG, "scanBLEDevices: ")
        val scanSettings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
            .build()

        val scanFilter = ScanFilter.Builder()
//            .setDeviceAddress("B4:99:4C:34:DC:8B") //TODO add the filters here when we know the device address by our database
            .build()


        rxBleClient.scanBleDevices(scanSettings, scanFilter)
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { dispose() }
            .subscribe({
                Log.d(TAG,
                    "scanBLEDevices: name ${it.bleDevice.name}, mac ${it.bleDevice.macAddress}"
                )
                addIfNeeded(it)
            },
                { onScanFailure(it) })
            .let { scanDisposable = it }
    }

    private fun addIfNeeded(it: ScanResult) {
        Log.d(TAG, "addIfNeeded: ${it.bleDevice.name}")
        val newDevice: BleDevice = BleDevice.getFromScanResult(it)

        if (_deviceList.value.contains(newDevice)) return

        Log.d(TAG, "add the device: ${it.bleDevice.name}")
        val newDevices: List<BleDevice> = (_deviceList.value ?: emptyList()).plus(newDevice)
        _deviceList.value = newDevices
    }

    private fun onScanFailure(throwable: Throwable) {
        if (throwable is BleScanException) showError(throwable)
        else  {
            Log.w(TAG, "Scan failed", throwable.cause)
        }
    }

    private fun showError(throwable: BleScanException) {
        Log.e(TAG, "Scan failed", throwable)
    }


    private fun dispose() {
        Log.d(TAG, "dispose: scanDisposable = null")
        scanDisposable = null
        _isScanning.value = false
    }

    fun stopScan() {
        Log.d(TAG, "stopScan: ")
        scanDisposable?.dispose()
        _isScanning.value = false
    }

    fun refresh() {
        Log.d(TAG, "refresh: ${rxBleClient.state}")
        _rxBleClientState.value = rxBleClient.state
    }

    fun startScan() {
        Log.d(TAG, "startScan: ")
        _rxBleClientState.value = rxBleClient.state
        scanBLEDevices()
    }

    fun getMessageBluetoothNotReady(): Int {
        Log.d(TAG, "getMessageBluetoothNotReady: ")
        return when (_rxBleClientState.value) {
            RxBleClient.State.BLUETOOTH_NOT_ENABLED -> R.string.bluetooth_not_enabled
            RxBleClient.State.LOCATION_PERMISSION_NOT_GRANTED -> R.string.location_not_granted
            RxBleClient.State.LOCATION_SERVICES_NOT_ENABLED -> R.string.location_services_not_granted
            RxBleClient.State.BLUETOOTH_NOT_AVAILABLE -> R.string.bluetooth_not_available
            else -> R.string.bluetooth_ready
        }
    }
}