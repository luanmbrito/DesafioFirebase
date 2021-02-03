package com.example.desafiofirebase.business

import android.net.Uri
import com.example.desafiofirebase.model.HomeModel
import com.example.desafiofirebase.repository.HomeRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.UploadTask

class HomeBusiness {

    private val repository by lazy{
        HomeRepository()
    }

    fun updateHomeCollection(doc: String): DocumentReference{
        return repository.updateHomeCollection(doc)
    }

     fun addHomeCollection(data: HashMap<String, String?>): Task<DocumentReference> {
        return repository.addHomeCollection(data)
    }

     fun saveGame(selectedPhotoUri: Uri): UploadTask {
        return repository.saveGame(selectedPhotoUri)
    }

    fun getAllCollection(user: String): Task<QuerySnapshot> {
       return repository.getAllCollection(user)
    }
}