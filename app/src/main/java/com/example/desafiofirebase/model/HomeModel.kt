package com.example.desafiofirebase.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeModel(
    public var id: String?,
    public var title: String?,
    public var image_url: String?,
    public var date:String?,
    public var descricao: String?,
    public var user: String?,
) : Parcelable
