package com.example.desafiofirebase.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.desafiofirebase.R
import com.example.desafiofirebase.model.HomeModel
import com.example.desafiofirebase.databinding.HomeListGamesBinding
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

class HomeAdapter(
    private val homeList : QuerySnapshot,
    private val onItemClicked: (HomeModel, Int) -> Unit
)
    : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HomeListGamesBinding
            .inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(homeList.elementAt(position), onItemClicked)
    }

    override fun getItemCount(): Int {
        return homeList.documents.size
    }


    class ViewHolder(private val binding: HomeListGamesBinding)
        : RecyclerView.ViewHolder(binding.root) {

            fun bind(home: QueryDocumentSnapshot,onItemClicked: (HomeModel,Int) -> Unit) = with(binding){

                var id = home.id as? String
                var title = home["title"] as? String
                var image_url = home["image_url"] as? String
                var date = home["date"] as? String
                var descricao = home["descricao"] as? String
                var user = home["user"] as? String

                    Glide.with(itemView.context)
                            .load(image_url)
                            .placeholder(R.drawable.ic_launcher_background)
                            .into(ivHomeImageGame)

                    tvHomeYear.text = home["date"] as String
                    tvHomeGame.text = home["title"] as String



                    var modelo = HomeModel(id,title,image_url,date,descricao,user)
                Log.i("modelo", "${modelo}")
                ivHomeContainer.setOnClickListener{
                        onItemClicked(modelo,this@ViewHolder.adapterPosition)
                    }
                }
        }

    }
