package com.example.desafiofirebase.database

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class HomeDao {

    private val db by lazy {
        FirebaseDB().database()
    }

    private val storage by lazy {
        FirebaseDB().storage()
    }

    private val auth by lazy {
        Firebase.auth
    }


    fun updateHomeCollection(doc: String): DocumentReference {
        return db.collection("Games")
                .document(doc)
    }
     fun addHomeCollection(data: HashMap<String, String?>): Task<DocumentReference> {
        return db.collection("Games")
            .add(data)
    }

     fun saveGame(selectedPhotoUri: Uri): UploadTask {
        val storageRef = storage.reference
      return selectedPhotoUri?.let { selected ->
           var url = "${auth.currentUser?.uid}/${System.currentTimeMillis()}.png"
           storageRef.child(url).putFile(selected)
          /*val ref = storageRef.child(url)
           ref.putFile(selected)
          return ref*/
           //return url
        }
    /*
        uploadTask?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this,"Deu bom", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this,"Deu Ruim", Toast.LENGTH_SHORT).show()
            }

        }*/
    }

    fun getAllCollection(user: String): Task<QuerySnapshot> {

       return db.collection("Games")
            .whereEqualTo("user",user)
           .get()
    }
}





