package com.thesushealthapp.remote_data.model

data class BloodGlucoseData(
    val userId: String,
    val timestamp: Long,
    val glucoseLevel: Float
)