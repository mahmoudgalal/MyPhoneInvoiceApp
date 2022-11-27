package com.mahmoudgalal.myphoneinvoice.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mahmoudgalal.myphoneinvoice.network.model.ServiceResponse.Account.Invoice

object Converters {
    @JvmStatic
    @TypeConverter
    fun fromJsonString(value: String?): List<Invoice.Detail>? {
        val gson = Gson()
        val listType = object : TypeToken<List<Invoice.Detail>?>() {}.type
        return gson.fromJson(value, listType)
    }

    @JvmStatic
    @TypeConverter
    fun listFromString(value: List<Invoice.Detail>?): String {
        val gson = Gson()
        return gson.toJson(value)
    }
}