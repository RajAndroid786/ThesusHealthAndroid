package com.thesushealthapp.remote_data.model

data class CholesterolData(
    val userId: String,
    val timestamp: Long,
    val totalCholesterol: Float,
    val ldlCholesterol: Float,
    val hdlCholesterol: Float,
    val triglycerides: Float
)