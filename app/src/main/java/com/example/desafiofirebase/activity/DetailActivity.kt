package com.example.desafiofirebase.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.desafiofirebase.R
import com.example.desafiofirebase.databinding.ActivityDetailBinding
import com.example.desafiofirebase.model.HomeModel
import com.example.desafiofirebase.viewmodel.HomeViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var homeModel: HomeModel? = null
    private lateinit var viewModel : HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeModel = intent.getParcelableExtra("firebaseGames")
        Log.i("detail", "${homeModel}")
        initComponents(homeModel)
        setup()
    }

    fun initComponents(data: HomeModel?){

        Glide.with(applicationContext)
            .load(data?.image_url)
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.ivDetailImage)

        binding.tvDetailTitleImage.text = data?.title
        binding.tvDetailTitle.text = data?.title
        binding.tvDetailYear.text = "Lançamento ${data?.date}"
        binding.tvDetailDesc.text = data?.descricao

    }


    override fun onRestart() {
        super.onRestart()
        finish()
    }

    fun setup(){
        binding.fabDetailUpdate.setOnClickListener {
            var intent = Intent(this@DetailActivity, AddActivity::class.java)
            intent.putExtra("editGames",homeModel)
            startActivity(intent)
        }
    }
}
