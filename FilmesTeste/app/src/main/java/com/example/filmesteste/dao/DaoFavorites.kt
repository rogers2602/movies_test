package com.example.filmesteste.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DaoFavorites(context: Context) : SQLiteOpenHelper(context, NOMEBANCO, null, VERSAOBANCO) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(TABLECARD)
    }

    override fun onUpgrade(db: SQLiteDatabase, i: Int, i1: Int) {
        db.execSQL("DROP TABLE IF EXISTS Cartao;")
    }

    companion object {

        private val NOMEBANCO = "Favorites"
        private val VERSAOBANCO = 1
        private val TABLECARD = ("CREATE TABLE Favorites (id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "title TEXT,"
                + "image TEXT,"
                + "genres TEXT,"
                + "data TEXT,"
                + "sinopese TEXT);")
    }
}