package com.example.filmesteste.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ex.DaoDate
import com.example.filmesteste.R
import com.example.filmesteste.adapter.AdapterFavo
import com.example.filmesteste.dao.DaoFavorites
import com.example.filmesteste.data.ResultFv
import kotlinx.android.synthetic.main.frag_favorites.*

class FragFavorites : Fragment() {

    private var regdb: DaoDate? = null
    private var bd: DaoFavorites? = null
    private var list: ArrayList<ResultFv>? = null
    private var recyclerView: RecyclerView? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater?.inflate(R.layout.frag_favorites, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        regdb = DaoDate(this!!.context!!)
        bd = DaoFavorites(this!!.context!!)

        if (regdb!!.favored().isEmpty()) {

        } else {
            list = regdb!!.favored()
            ListaRecycler(list!!)

        }

    }

    fun ListaRecycler(dados: ArrayList<ResultFv>) {
        progress_favo.visibility = View.VISIBLE

        layoutManager = LinearLayoutManager(this!!.context!!, LinearLayoutManager.VERTICAL, false)
        recyclerView = recycler_favo

        val mStaggeredVerticalLayoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL) // (int spanCount, int orientation)
        recyclerView!!.setLayoutManager(mStaggeredVerticalLayoutManager)
        recyclerView!!.setLayoutManager(mStaggeredVerticalLayoutManager)

        recyclerView!!.setAdapter(AdapterFavo(dados, this!!.context!!))

        progress_favo.visibility = View.GONE
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