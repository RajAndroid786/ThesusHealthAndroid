package com.thesushealth.data

import androidx.room.TypeConverter
import com.polidea.rxandroidble3.RxBleConnection


class Converter {

    @TypeConverter
    fun fromBleConnectionState(state: RxBleConnection.RxBleConnectionState): String {
        return state.name
    }

    @TypeConverter
    fun toBleConnectionState(priority: String): RxBleConnection.RxBleConnectionState {
        return RxBleConnection.RxBleConnectionState.valueOf(priority)
    }

}