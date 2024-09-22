package com.pankajjangid.cryptotest.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pankajjangid.cryptotest.local.entity.CurrencyEntity

object Utils {

    fun getFileFromJson(context: Context, fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }

    fun parseJsonToModel(jsonString: String): List<CurrencyEntity> {
        val gson = Gson()

        return gson.fromJson(jsonString, object : TypeToken<List<CurrencyEntity>>() {}.type)
    }
}