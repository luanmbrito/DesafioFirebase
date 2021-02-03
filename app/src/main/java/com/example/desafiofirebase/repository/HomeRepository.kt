package com.example.desafiofirebase.repository

import android.net.Uri
import com.example.desafiofirebase.database.HomeDao
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.UploadTask

class HomeRepository {

    private val homeDao by lazy{
        HomeDao()
    }

    fun updateHomeCollection(doc: String): DocumentReference{
        return homeDao.updateHomeCollection(doc)
    }
     fun addHomeCollection(data: HashMap<String, String?>): Task<DocumentReference> {
        return homeDao.addHomeCollection(data)
    }

     fun saveGame(selectedPhotoUri: Uri): UploadTask {
        return homeDao.saveGame(selectedPhotoUri)
    }

    fun getAllCollection(user: String): Task<QuerySnapshot> {

        return homeDao.getAllCollection(user)
    }
}