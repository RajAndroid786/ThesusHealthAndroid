package com.thesushealthapp.remote_data.model

data class MedicationSet(
    val user: String,
    val timestamp: Long,
    val medications: List<Medication>
)