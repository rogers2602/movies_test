package com.example.filmesteste

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ex.DaoDate
import com.example.filmesteste.dao.DaoFavorites
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detalhes.*
import java.text.SimpleDateFormat
import java.util.*
import com.example.filmesteste.data.ResultFv


class Detalhes : AppCompatActivity() {

    //title , genres , cover , dropcover , overview , date
    private var date: Date? = null
    private var dateS: String? = null
    private var title: String? = null
    private var genres: String? = null
    private var cover: String? = null
    private var dropcover: String? = null
    private var overview: String? = null
    private var bd: DaoFavorites? = null
    private var regdb: DaoDate? = null
    private var itemfv: ResultFv? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)

        regdb = DaoDate(applicationContext)
        bd = DaoFavorites(applicationContext)

        if (intent.extras.containsKey("title")) {
            title = intent.extras.get("title") as String
            genres = intent.extras.get("genres") as String
            cover = intent.extras.get("cover") as String
            overview = intent.extras.get("overview") as String

            if (intent.extras.containsKey("fv")){
                dateS = intent.extras.get("date") as String
                val format = SimpleDateFormat("dd-MM-yyyy")
                date = format.parse(dateS)
            }else {
                date = intent.extras.get("date") as Date
            }



            itemfv = ResultFv(0, title!!, cover!!, genres!!, padraoBR(date!!), overview!!)
        }



        val ab = supportActionBar

        if (intent.extras.containsKey("title")) {

            Picasso.get()
                .load(getString(com.example.filmesteste.R.string.base) + cover)
                .into(cover_movie)

            if (title != null) {
                if (ab != null) {
                    ab.title = title
                }
            } else {
                Toast.makeText(applicationContext, getString(R.string.error), Toast.LENGTH_SHORT).show()
            }

            //generos
            genres_list.text = genres
            // titulo
            name_f.text = title
            //data lançamento
            date_release.text = "Lançamento: " + padraoBR(this!!.date!!)
            //
            overview_movie.text = overview

        }

        favorite.setOnClickListener() {
            inserir(itemfv!!)
        }


    }

    private fun inserir(item: ResultFv) {
        if (regdb!!.favorite_ins(item) > 0) {
            favorite.setImageDrawable(resources.getDrawable(R.drawable.heart))
            Toast.makeText(applicationContext, "Salvo nos favoritos!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "Houve um erro inesperado!", Toast.LENGTH_SHORT).show()
        }

    }

    fun padraoBR(date: Date): String {
        val format = SimpleDateFormat("dd/MM/yyy")
        return format.format(date)
    }

}
