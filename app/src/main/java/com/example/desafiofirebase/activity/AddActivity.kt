 package com.example.desafiofirebase.activity

import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.desafiofirebase.databinding.ActivityAddBinding
import com.example.desafiofirebase.model.HomeModel
import com.example.desafiofirebase.utils.Extensions.toEditable
import com.example.desafiofirebase.viewmodel.HomeViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class AddActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddBinding
    var selectedPhotoUri : Uri? = null

    private val auth by lazy {
        Firebase.auth
    }

    private var homeModel: HomeModel? = null

    private lateinit var viewModel : HomeViewModel

    private lateinit var data: HashMap<String,String?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeModel = intent.getParcelableExtra("editGames")
        setup()
        initComponents()

    }
    fun initComponents(){
        homeModel?.let {
            Glide.with(this)
                    .load(it.image_url)
                    .circleCrop()
                    .into(binding.fabAddPhoto)
             binding.tieAddDate.text = it.date?.toEditable()
             binding.tieAddDescription.text = it.descricao?.toEditable()
             binding.tieAddName.text = it.title?.toEditable()

        }

        viewModel.isSave.observe(this){
            if(it) {
                Toast.makeText(this, "Salvo com sucesso!",Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                Toast.makeText(this, "Atualizado com sucesso!",Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    fun setup(){

        binding.fabAddPhoto.setOnClickListener{
            selectFoto()
        }

        binding.btnAddSave.setOnClickListener{

            auth.currentUser?.let { authentication ->
                    selectedPhotoUri?.let { it ->
                        viewModel.saveGame(it/*,binding,homeModel?.id*/)
                        viewModel.saveLiveData.observe(this){
                            it.storage.downloadUrl.addOnSuccessListener { uri ->
                                selectedPhotoUri = uri
                                data = map()
                                data.put("image_url",uri.toString())
                                insertOrUpdate()
                            }
                         }
                      } ?: run {
                        data = map()
                        data.put("image_url",homeModel?.image_url)
                        insertOrUpdate()
                    }
            }
        }
    }

    fun insertOrUpdate(){
        homeModel?.id?.let{ id ->
            viewModel.updateHomeCollection(id,data)
        } ?: viewModel.addHomeCollection(data)
    }

    fun map(): HashMap<String,String?>{

        return hashMapOf(
                "date" to binding.tieAddDate.text.toString(),
                "descricao" to binding.tieAddDescription.text.toString(),
                "title" to binding.tieAddName.text.toString(),
                "user" to auth.uid.toString())

    }

    private fun selectFoto() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0) {
            selectedPhotoUri = data?.data
            try {
                selectedPhotoUri?.let { uri->
                    if (Build.VERSION.SDK_INT < 28) {
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            this.contentResolver,
                            selectedPhotoUri
                        )
                        binding.fabAddPhoto.setImageBitmap(bitmap)
                    } else {

                        val source =
                                ImageDecoder.createSource(this.contentResolver, uri)
                        val bitmap = ImageDecoder.decodeBitmap(source)
                        var drawable = bitmap.toDrawable(resources)
                        Glide.with(this@AddActivity)
                            .clear(binding.fabAddPhoto)
                        binding.fabAddPhoto.background = drawable
                    }
                }


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}