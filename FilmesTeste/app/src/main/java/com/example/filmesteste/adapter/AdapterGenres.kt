package com.example.filmesteste.adapter

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmesteste.ActivityMoviesGenre
import com.example.filmesteste.data.Genres

import java.io.Serializable
import android.app.Activity
import com.example.filmesteste.R


class AdapterGenres (private var items: Genres,private var context: Context): RecyclerView.Adapter<AdapterGenres.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
            .inflate(com.example.filmesteste.R.layout.ly_row_categories, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var itens = items.listgenres!![position]
        holder!!.txtName?.text = itens.name

        holder.txtName!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val activity = Intent(context.getApplicationContext(), ActivityMoviesGenre::class.java)
                //passando nome da marca pelo put extra

                activity.putExtra("genre", items.listgenres!![position] as Serializable)
                // myactivity.putExtra("Pasta", Nomesoff.get(position).getPasta())
                activity.addFlags(FLAG_ACTIVITY_NEW_TASK)
                (context as Activity).finish()
                context.startActivity(activity)

            }
        })

    }

    override fun getItemCount(): Int {
        return items.listgenres!!.size
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        var txtName: TextView? = null

        init {
            this.txtName = row?.findViewById(R.id.genre)
            //this.txtComment = row?.findViewById<TextView>(R.id.txtComment)
        }
    }
}