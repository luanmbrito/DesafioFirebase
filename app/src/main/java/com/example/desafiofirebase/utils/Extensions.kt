package com.example.desafiofirebase.utils

import android.text.Editable

object Extensions {

    fun String.toEditable() : Editable{
       return Editable.Factory.getInstance().newEditable(this)
    }
}