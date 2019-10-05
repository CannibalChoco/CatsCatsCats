package com.kglazuna.app.repository

import com.kglazuna.app.BuildConfig
import com.kglazuna.app.api.CatApi
import com.kglazuna.app.model.Cat
import timber.log.Timber

object CatRepo {

    private val catApi = CatApi.create()

    suspend fun getCats2() : List<Cat> {
        val apiResponse = catApi.getCats(BuildConfig.CAT_API_KEY,40)
        Timber.d("response: $apiResponse")
        val catList = apiResponse.body()
        return when (catList) {
            is List<Cat> -> {
                Timber.d("got cats: $catList")
                Timber.d("CatRepo: Cat count: ${catList.size}")
                catList
            }
            else -> {
                Timber.d("got something other than cats")
                emptyList()
            }
        }
    }
}
