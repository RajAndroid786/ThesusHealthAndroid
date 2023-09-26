package com.thesushealthapp.remote_data.firebase_controller

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.thesushealthapp.BuildConfig
import com.thesushealthapp.util.TimeUtil
import java.util.UUID

private const val PATIENT_COLLECTION_DEV = "patient_health_info_dev"
private const val PATIENT_COLLECTION_PROD = "patient_health_info"

class FirebaseController {


    companion object {

        val TAG = "FirebaseController"

        //save patient health info on the Firebase database, use only this method to save patient health info
        //TODO only for test for now, waiting for the Victor to make it properly
        fun savePatientHealthInfo(data: Map<String, Any>) {
            val documentId = generateDocumentId();
            val userData = hashMapOf(
                "userName" to "Thiago Neves",
                "userIde" to "1234567",
            )

            Firebase.firestore.collection(getCollectionByEnvironment())
                .document(documentId).set(userData) //TODO get the userId from the user
                .addOnSuccessListener {
                    Firebase.firestore.collection(getCollectionByEnvironment())
                        .document(documentId)
                        .collection("daily_data")
                        .document(TimeUtil.getDateFormattedNow())
                        .set(data)
                        .addOnSuccessListener {
                        Log.d(TAG,  "DocumentSnapshot successfully written!")
                        }
                        .addOnFailureListener { e ->
                            Log.d(TAG, "Error writing document")
                        }
                }
        }

    private fun getCollectionByEnvironment() = if
            (BuildConfig.DEBUG) PATIENT_COLLECTION_DEV
        else PATIENT_COLLECTION_PROD

    private fun generateDocumentId() =
         UUID.randomUUID().toString()
    }
}
