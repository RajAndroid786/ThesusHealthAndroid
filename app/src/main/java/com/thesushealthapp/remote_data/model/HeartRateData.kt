package com.thesushealthapp.remote_data.model

data class HeartRateData(
    val userId: String,
    val timestamp: Long,
    val heartRate: Int
)