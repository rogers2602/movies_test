package com.example.filmesteste.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Genres {
    @SerializedName("genres")
    val listgenres: List<Genre>? = null
}

class Genre : Serializable {
    val id: Long = 0
    val name: String = ""
}
