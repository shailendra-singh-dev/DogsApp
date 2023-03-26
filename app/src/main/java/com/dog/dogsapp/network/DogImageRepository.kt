package com.dog.dogsapp.network

import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DogImageRepository {
    private val url = "https://dog.ceo/api/"

    private val itemService: DogService
    val itemsLiveData: MutableLiveData<ArrayList<String>> = MutableLiveData<ArrayList<String>>()
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create()).build()
        itemService = build.create(DogService::class.java)
    }

    fun getPosts(breedName: String) {
        itemService.getBreedImages(breedName).enqueue(object : Callback<Dog> {
            override fun onResponse(call: Call<Dog>, response: Response<Dog>) {
                if (response.isSuccessful) {
                    itemsLiveData.postValue(response.body()?.message)
                    errorMessageLiveData.postValue("")
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                }
            }

            override fun onFailure(call: Call<Dog>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
            }
        })
    }
}
