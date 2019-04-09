package com.example.filmesteste.data

import com.google.gson.annotations.SerializedName
import java.util.*

class MovieOBJ (

        @SerializedName("results")
        val results: ArrayList<Result>,
        val page: Long,
        val total_results: Long,
        val dates: Dates,
        val total_pages: Long
    )

    data class Dates (
        val maximum: String,
        val minimum: String
    )

    data class Result  (
        val vote_count: Long?,
        var id: Long?,
        val video: Boolean,
        val vote_average: Double,
        var title: String,
        val popularity: Double,
        var poster_path: String,
        val original_language: OriginalLanguage?,
        val original_title: String,
        @SerializedName("genre_ids")
        val genreIDS: List<Long>,
        val backdrop_path: String,
        val adult: Boolean,
        var overview: String,
        var release_date: Date
    )

    enum class OriginalLanguage {
        CN,
        En,
        Fr,
        No
    }
