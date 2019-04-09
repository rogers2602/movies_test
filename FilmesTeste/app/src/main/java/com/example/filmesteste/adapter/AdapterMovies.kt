package com.example.filmesteste.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmesteste.ActivityMoviesGenre
import com.example.filmesteste.Detalhes
import com.example.filmesteste.data.MovieOBJ
import com.example.filmesteste.R
import com.example.filmesteste.data.Genre
import com.example.filmesteste.data.Genres
import com.example.filmesteste.data.Result
import com.squareup.picasso.Picasso
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AdapterMovies(private var items: ArrayList<Result>, private var genreid: Genre?, private var context: Context) :
    RecyclerView.Adapter<AdapterMovies.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
            .inflate(R.layout.ly_row_rommovie, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var movie = this.items[position]

        holder?.nameMovie?.text = movie.title

        holder.genresname?.text = genres(movie.genreIDS)

        holder.date?.text = padraoBR(movie.release_date)

        Picasso.get()
            .load(context.getString(com.example.filmesteste.R.string.base) + movie.poster_path)
            .into(holder.coverMovie)

        holder.item_movie!!.setOnClickListener(){
            val activity = Intent(context.getApplicationContext(), Detalhes::class.java)
            //passando nome da marca pelo put extra

            activity.putExtra("title", items[position].title)
            activity.putExtra("genres", genres(movie.genreIDS))
            activity.putExtra("cover", items[position].poster_path)
            activity.putExtra("dropcover", items[position].backdrop_path)
            activity.putExtra("overview", items[position].overview)
            activity.putExtra("date", items[position].release_date)

            // myactivity.putExtra("Pasta", Nomesoff.get(position).getPasta())
            activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(activity)
        }


    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun padraoBR(date: Date): String {
        val format = SimpleDateFormat("dd/MM/yyy")
        return format.format(date)
    }

    fun filterList(revert : Boolean) {

        // false orderna normalmente crescente
        // true ordena crescente e reverte a lista.

        var usuarioListAtt: ArrayList<Result> = ArrayList()
        val dates: ArrayList<Date> = ArrayList()

        // separa as datas
        for (i in items.indices) {
            dates.add(items[i].release_date)
        }

        // ordena datas
        Collections.sort(dates)

        // ordena elementos de acordo com lista de datas
        for (i in items.indices) {
            for (j in items) {
                if (dates[i].equals(j.release_date)) {
                    if (!usuarioListAtt.contains(j)) {
                        usuarioListAtt.add(j)
                    }
                }
            }
        }
        items.clear()
        items = usuarioListAtt

        if (revert){
            Collections.reverse(items)
        }

        notifyDataSetChanged()
    }

    fun genreslist(vl: Int): String {
        val genre = when (vl) {
            28 -> "Ação"
            12 -> "Aventura"
            16 -> "Animação"
            35 -> "Comédia"
            80 -> "Crime"
            99 -> "Documentário"
            18 -> "Drama"
            10751 -> "Família"
            14 -> "Fantasia"
            36 -> "História"
            27 -> "Terror"
            10402 -> "Música"
            9648 -> "Mistério"
            10749 -> "Romance"
            878 -> "Ficção científica"
            10770 -> "Cinema TV"
            53 -> "Thriller"
            10752 -> "Guerra"
            37 -> "Faroeste"
            else -> "..."
        }
        return genre
    }


    fun genres(list: List<Long>): String {
        var genress = ""

        for (i in list.indices) {
            if ((i-1) == list.size) {
                genress += genreslist(list.get(i).toInt())
            } else {
                if ((i-1) == list.size) {
                    genress += genreslist(list.get(i).toInt())
                }else{
                    genress += genreslist(list.get(i).toInt()) + ", "
                }
            }
        }

        return genress
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        var nameMovie: TextView? = null
        var coverMovie: ImageView? = null
        var item_movie: LinearLayout? = null
        var genresname: TextView? = null
        var date: TextView? = null

        init {
            this.nameMovie = row?.findViewById(R.id.title)
            this.coverMovie = row?.findViewById(R.id.cover_movie_item)
            this.item_movie = row?.findViewById(R.id.item_movie)
            this.genresname = row?.findViewById(R.id.generos)
            this.date = row?.findViewById(R.id.date)
        }
    }
}