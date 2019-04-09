package com.ex

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.filmesteste.dao.DaoFavorites
import com.example.filmesteste.data.ResultFv
import java.util.*


class DaoDate( var context: Context) {
    private var dao: DaoFavorites? = null

    fun favorite_ins(daoFavorites: ResultFv): Long {
        dao = DaoFavorites(context)
        val db = dao!!.getWritableDatabase()
        val dados = favori_obj(daoFavorites)
        val inserir = db.insert(BANCONOME, null, dados)
        db.close()
        Log.i(BANCONOME, inserir.toString() + "")
        return inserir
    }

    fun favored(): ArrayList<ResultFv> {
        val sql = "SELECT * FROM $BANCONOME"
        dao = DaoFavorites(context)
        val db = dao!!.getReadableDatabase()
        val c = db.rawQuery(sql, null)
        val Cartao = ArrayList<ResultFv>()
        while (c.moveToNext()) {
            val fv : ResultFv = ResultFv(null,null,null,null,null,null)
            fv.id = (c.getLong(c.getColumnIndex("id")))
            fv.title  = (c.getString(c.getColumnIndex("title")))
            fv.poster_path = (c.getString(c.getColumnIndex("image")))
            fv.release_date = c.getString(c.getColumnIndex("data"))
            fv.genreIDS = c.getString(c.getColumnIndex("genres"))
            fv.overview = (c.getString(c.getColumnIndex("sinopese")))
            Cartao.add(fv)
        }
        c.close()
        return Cartao
    }

    fun Deletar_cartao(item: ResultFv) {
        dao = DaoFavorites(context)
        val db = dao!!.getWritableDatabase()
        val whereClause = "id=?"
        val whereArgs = arrayOf<String>(item.id.toString())
        db.delete(BANCONOME, whereClause, whereArgs)
        db.close()
    }

    fun favori_obj(item: ResultFv): ContentValues {
        val dados = ContentValues()
        dados.put("title", item.title)
        dados.put("image", item.poster_path)
        dados.put("data",(item.release_date).toString())
        dados.put("genres",item.genreIDS)
        dados.put("sinopese", item.overview)
        return dados
    }

    companion object {
        private val BANCONOME = "Favorites"
    }

}