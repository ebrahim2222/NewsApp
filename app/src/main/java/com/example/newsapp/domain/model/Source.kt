package com.example.newsapp.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("id")
    @Expose
    val id:Any ,
    @SerializedName("name")
    @Expose
    val name:String,
)
