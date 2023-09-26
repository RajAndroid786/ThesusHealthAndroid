package com.thesushealthapp.remote_data.model

data class BodyTemperatureData(
    val userId: String,
    val timestamp: Long,
    val temperature: Float
)