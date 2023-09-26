package com.thesushealthapp.remote_data.model

data class BloodPressureData(
    val userId: String,
    val timestamp: Long,
    val systolic: Int,
    val diastolic: Int
)