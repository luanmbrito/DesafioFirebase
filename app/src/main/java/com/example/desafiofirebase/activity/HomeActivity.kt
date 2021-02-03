package com.example.desafiofirebase.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.desafiofirebase.adapter.HomeAdapter
import com.example.desafiofirebase.database.FirebaseDB
import com.example.desafiofirebase.databinding.ActivityHomeBinding
import com.example.desafiofirebase.viewmodel.HomeViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val auth by lazy {
        Firebase.auth
    }

    private val db by lazy {
        FirebaseDB().database()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        initComponents()
    }

    private fun initComponents(){
        binding.fabHomeAdd.setOnClickListener {
            var intent = Intent(this,AddActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        auth.currentUser?.let {
            viewModel.getAllCollection(it.uid)
            viewModel.homeLiveData.observe(this) { snapshot ->
                binding.rvGames.apply {
                    layoutManager = GridLayoutManager(this.context, 2)

                    adapter = HomeAdapter(snapshot) { home, position ->
                        val intent = Intent(this@HomeActivity, DetailActivity::class.java)
                        intent.putExtra("firebaseGames", home)
                        startActivity(intent)
                    }
                }
            }
        }?: run {
            signIn()
        }
    }

    private fun signIn() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}