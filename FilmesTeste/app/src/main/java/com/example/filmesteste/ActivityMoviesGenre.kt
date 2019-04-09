package com.example.filmesteste

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.filmesteste.adapter.AdapterMovies
import com.example.filmesteste.data.Genre
import com.example.filmesteste.data.MovieOBJ
import com.example.filmesteste.data.Result
import com.example.filmesteste.utils.GsonConverter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.frag_list_categories.*
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class ActivityMoviesGenre : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var page: Long?? = null
    private var genreId: Genre?? = null
    private var totalpg: Int? = null
    private var clickpage: Boolean = false
    private var adapterMv : AdapterMovies? = null
    private var listGenre: ArrayList<Result>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pages.visibility = View.GONE

        if (intent.extras.containsKey("genre")) {
            genreId = intent.extras.get("genre") as Genre
        }

        val ab = supportActionBar


        // actionbar / verificando extra
        if (genreId != null) {
            if (ab != null) {
                ab.title = "Filmes de " + genreId!!.name
            }
        } else {
            Toast.makeText(applicationContext, getString(R.string.error), Toast.LENGTH_SHORT).show()
        }


        // verifica se tem internet se tem, lista os filmes

        if (haveNetworkConnection() and (genreId != null)) {
            progress_movie.visibility = View.VISIBLE
            HttpService().execute()
            page = 1
        } else {
            Toast.makeText(applicationContext, getString(R.string.conect_internet), Toast.LENGTH_SHORT).show()
        }

        // Páginas

        pg1.setOnClickListener {numeroClicadoBT1(pg1.text.toString().toInt())}
        pg2.setOnClickListener {numeroClicadoBT2(pg2.text.toString().toInt())}
        pg3.setOnClickListener {numeroClicadoBT3(pg3.text.toString().toInt())}


        /*order.setOnClickListener{
            adapterMv!!.filterList()
        }*/

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        startActivity(Intent(this, Main::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()

        if (id == R.id.cres) {
            adapterMv!!.filterList(false)
            Toast.makeText(this, "Ordem crescente", Toast.LENGTH_LONG).show()
            return true
        }
        if (id == R.id.decres) {
            adapterMv!!.filterList(true)
            Toast.makeText(this, "Ordem decrescente", Toast.LENGTH_LONG).show()
            return true
        }

        return super.onOptionsItemSelected(item)

    }

    fun ListaRecycler(dados: ArrayList<Result>) {

        layoutManager = LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView = recycler_movies

        val mStaggeredVerticalLayoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL) // (int spanCount, int orientation)
        recyclerView!!.setLayoutManager(mStaggeredVerticalLayoutManager)
        recyclerView!!.setLayoutManager(mStaggeredVerticalLayoutManager)

        adapterMv = AdapterMovies(dados, genreId, getApplicationContext())

        recyclerView!!.setAdapter(adapterMv)

        progress_movie.visibility = View.GONE
    }

    inner class HttpService : AsyncTask<String, String, MovieOBJ>() {

        override fun onPreExecute() {
            super.onPreExecute()
            progress_movie.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg voids: String): MovieOBJ? {
            val response = StringBuilder()
            val jsonResponse = GsonConverter()

            try {
                val url = URL(getString(R.string.movies) + page)

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
                jsonResponse.getObj(response.toString(), MovieOBJ::class.java)
            } else null
        }

        override fun onPostExecute(objList: MovieOBJ) {
            //progressBar.setVisibility(View.GONE);

            if (objList.results != null && !objList.results!!.isEmpty()) {
                // lista modificada

                listGenre!!.clear()

                for (i in objList.results.indices) {
                    if (objList.results[i].genreIDS.contains(genreId!!.id)) {
                        listGenre!!.add(objList.results[i])
                    }
                }

                ListaRecycler(listGenre!!)

                // para não zerar contador de páginas
                if (!clickpage) {
                    pages(objList.total_pages)
                    clickpage = true
                }
                totalpg = objList.total_pages.toInt()

            }
        }
    }

    fun pages(pagesNum: Long) {

        pages.visibility = View.VISIBLE;

        if (pagesNum >= 3) {
            pg1.text = "1"
            pg2.text = "2"
            pg3.text = "3"
            pg1.visibility = View.VISIBLE
            pg2.visibility = View.VISIBLE
            pg3.visibility = View.VISIBLE
        }

        // equals por causa do long
        if (pagesNum.equals(2)) {
            pg1.text = "1"
            pg2.text = "2"
            pg1.visibility = View.VISIBLE
            pg2.visibility = View.VISIBLE
        }

    }

    fun numeroClicadoBT1(clicknum: Int) {

        if (clicknum == 1 || (clicknum == 2 && clicknum!=pg1.text.toString().toInt())) {
            //nada
            page = clicknum.toLong();
            HttpService().execute()
        } else {
            if (pg1.text.toString().toInt() > 1) {
                pg1.text = ((pg1.text.toString().toInt() - 1).toString())
                pg2.text = ((pg2.text.toString().toInt() - 1).toString())
                pg3.text = ((pg3.text.toString().toInt() - 1).toString())
                page = clicknum.toLong();
                HttpService().execute()
            }
        }
    }
    fun numeroClicadoBT2(clicknum: Int) {
        pages.visibility = View.VISIBLE
        page = clicknum.toLong();
        HttpService().execute()
    }

    fun numeroClicadoBT3(clicknum: Int) {
        pages.visibility = View.VISIBLE
        if (clicknum == 1 || clicknum == 2) {
            //nada
            page = clicknum.toLong();
            HttpService().execute()
        } else {
            if (((clicknum + 1) <= totalpg!!)) {
                pg1.text = ((pg1.text.toString().toInt() + 1).toString())
                pg2.text = ((pg2.text.toString().toInt() + 1).toString())
                pg3.text = ((pg3.text.toString().toInt() + 1).toString())
                page = clicknum.toLong()
                HttpService().execute()
            }
        }

    }

    private fun haveNetworkConnection(): Boolean {
        var haveConnectedWifi = false
        var haveConnectedMobile = false

        val cm =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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
