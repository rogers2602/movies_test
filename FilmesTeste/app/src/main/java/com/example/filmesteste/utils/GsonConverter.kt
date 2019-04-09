package com.example.filmesteste.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GsonConverter {

    fun <T> getList(jsonArray: String, clazz: Class<T>): List<T>? {
        val typeOfT = TypeToken.getParameterized(List::class.java, clazz).type
        return Gson().fromJson<List<T>>(jsonArray, typeOfT)
    }

    fun <T> getObj(jsonArray: String, clazz: Class<T>): T {
        return Gson().fromJson(jsonArray.toString(), clazz)
    }
}