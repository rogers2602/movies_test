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
import com.example.filmesteste.Detalhes
import com.example.filmesteste.DetalhesFv
import com.example.filmesteste.R
import com.example.filmesteste.data.Genre
import com.example.filmesteste.data.Result
import com.example.filmesteste.data.ResultFv
import com.squareup.picasso.Picasso

class AdapterFavo(private var items: ArrayList<ResultFv>, private var context: Context) :
    RecyclerView.Adapter<AdapterFavo.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterFavo.ViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
            .inflate(R.layout.ly_row_rommovie, parent, false)

        return AdapterFavo.ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdapterFavo.ViewHolder, position: Int) {
        var movie = this.items[position]

        holder?.nameMovie?.text = movie.title

        holder.genresname?.text = movie.genreIDS

        holder.date?.text = movie.release_date

        Picasso.get()
            .load(context.getString(com.example.filmesteste.R.string.base) + movie.poster_path)
            .into(holder.coverMovie)

        holder.item_movie!!.setOnClickListener() {
            val activity = Intent(context.getApplicationContext(), DetalhesFv::class.java)
            //passando nome da marca pelo put extra

            activity.putExtra("title", movie.title)
            activity.putExtra("genres", movie.genreIDS)
            activity.putExtra("cover", movie.poster_path)
            activity.putExtra("fv", true)
            activity.putExtra("overview", movie.overview)
            activity.putExtra("date", movie.release_date)

            // myactivity.putExtra("Pasta", Nomesoff.get(position).getPasta())
            activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(activity)
        }


    }

    override fun getItemCount(): Int {
        return items.size
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


