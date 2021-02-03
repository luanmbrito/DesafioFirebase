package com.example.desafiofirebase.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desafiofirebase.business.HomeBusiness
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.UploadTask

class HomeViewModel : ViewModel() {
    val homeLiveData : MutableLiveData<QuerySnapshot> = MutableLiveData()
    val saveLiveData : MutableLiveData<UploadTask.TaskSnapshot> = MutableLiveData()
    val isSave : MutableLiveData<Boolean> = MutableLiveData()
    private val homeBusiness = HomeBusiness()

    private val auth by lazy {
        Firebase.auth
    }


    fun getAllCollection(user: String){
        homeBusiness.getAllCollection(user)
                .addOnSuccessListener { snapshot ->
                    homeLiveData.postValue(snapshot)
                }
    }

    fun addHomeCollection(data: HashMap<String, String?>){
        homeBusiness.addHomeCollection(data)
                .addOnSuccessListener {
                    isSave.postValue(true)
                }
    }

    fun updateHomeCollection(doc: String,data: HashMap<String, String?>){
        homeBusiness.updateHomeCollection(doc)
                .set(data).addOnSuccessListener {
                    isSave.postValue(false)
                }
    }

    fun saveGame(selectedPhotoUri: Uri) {
            homeBusiness.saveGame(selectedPhotoUri).addOnSuccessListener {
                saveLiveData.postValue(it)
            }
    }


}

