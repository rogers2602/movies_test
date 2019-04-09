package com.example.filmesteste.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.filmesteste.adapter.AdapterGenres
import com.example.filmesteste.data.Genres
import com.example.filmesteste.R
import com.example.filmesteste.utils.GsonConverter
import kotlinx.android.synthetic.main.frag_list_categories.*
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*


class FragCategories : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.frag_list_categories, container, false)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (haveNetworkConnection()) {
            progress_genres.visibility = View.VISIBLE
            HttpService().execute()
        } else {
            Toast.makeText(context, getString(R.string.conect_internet), Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun newInstance(): FragCategories = FragCategories()
    }

    inner class HttpService : AsyncTask<String, String, Genres>() {

        override fun doInBackground(vararg voids: String): Genres? {
            val response = StringBuilder()

            val jsonResponse = GsonConverter()

            try {
                val url = URL(getString(R.string.genres))

                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-type", "application/text")
                connection.setRequestProperty("Accept", "application/text")
                connection.doOutput = true
                connection.connectTimeout = 5000
                connection.connect()

                val scanner = Scanner(url.openStream())
                while (scanner.hasNext()) {
                    response.append(scanner.nextLine())
                }
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return if (response.length > 0) {
                jsonResponse.getObj(response.toString(), Genres::class.java)
            } else null
        }

        override fun onPostExecute(objList: Genres) {
            //progressBar.setVisibility(View.GONE);

            if (objList.listgenres != null && !objList.listgenres!!.isEmpty()) {
                // lista original.
                ListaRecycler(objList)

            }
        }
    }

    fun ListaRecycler(dados: Genres) {

        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView = recycler_genres as RecyclerView?

        val mStaggeredVerticalLayoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL) // (int spanCount, int orientation)
        recyclerView!!.setLayoutManager(mStaggeredVerticalLayoutManager)
        recyclerView!!.setLayoutManager(mStaggeredVerticalLayoutManager)
        recyclerView!!.setAdapter(AdapterGenres(dados, this!!.context!!))

        progress_genres.visibility = View.GONE
    }


    private fun haveNetworkConnection(): Boolean {
        var haveConnectedWifi = false
        var haveConnectedMobile = false

        val cm =
            context!!.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        @SuppressLint("MissingPermission") val netInfo = cm.allNetworkInfo
        for (ni in netInfo) {
            if (ni.typeName.equals("WIFI", ignoreCase = true))
                if (ni.isConnected)
                    haveConnectedWifi = true
            if (ni.typeName.equals("MOBILE", ignoreCase = true))
                if (ni.isConnected)
                    haveConnectedMobile = true
        }
        return haveConnectedWifi || haveConnectedMobile
    }



}