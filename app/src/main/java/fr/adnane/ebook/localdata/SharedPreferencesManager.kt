package fr.adnane.ebook.localdata

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import fr.adnane.ebook.models.Items
import fr.adnane.ebook.models.LocalBookStorage

class SharedPreferencesManager {
    companion object {
        const val bookListKey = "bookListKey"
        const val searchKey = "searchKey"
        const val preferencesFile = "preferencesFile"
    }

    fun saveSearchCriteria(search:String, context: Context) {
        val sharedPreferences = context.getSharedPreferences(preferencesFile, Context.MODE_PRIVATE)
        sharedPreferences
            .edit()
            .putString(searchKey, search)
            .apply()
    }

    fun getSearchCriteria(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(preferencesFile, Context.MODE_PRIVATE)
        return sharedPreferences.getString(searchKey,"")
    }

    fun getLocalBookStorage(context: Context): LocalBookStorage {
        val sharedPreferences = context.getSharedPreferences(preferencesFile, Context.MODE_PRIVATE)
        val gson = Gson()
        val json: String? = sharedPreferences.getString(bookListKey, "")
        if (json.isNullOrEmpty()) {
            return LocalBookStorage(mutableListOf())
        }
        return gson.fromJson(json, LocalBookStorage::class.java)
    }

    fun saveBook(volume: Items, context: Context): Boolean {
        val localBookStorage = getLocalBookStorage(context)
        if (localBookStorage.localBooks.contains(volume)) {
            return false
        }
        localBookStorage.localBooks.add(volume)
        Log.d("savePhoto", " size "+localBookStorage.localBooks.size)
        val sharedPreferences = context.getSharedPreferences(preferencesFile, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = gson.toJson(localBookStorage)
        sharedPreferences.edit().putString(bookListKey, json).apply()
        return true
    }

    fun deleteBook(volume: Items, context: Context) {
        val localBookStorage = getLocalBookStorage(context)
        localBookStorage.localBooks.remove(volume)
        val sharedPreferences = context.getSharedPreferences(preferencesFile, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = gson.toJson(localBookStorage)
        sharedPreferences.edit().putString(bookListKey, json).apply()
    }
}