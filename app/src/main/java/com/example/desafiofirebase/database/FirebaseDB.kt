package com.example.desafiofirebase.database

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class FirebaseDB {

    fun database(): FirebaseFirestore {
        return Firebase.firestore
    }

    fun storage(): FirebaseStorage {
        return Firebase.storage
    }

}