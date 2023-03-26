package com.dog.dogsapp.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DogRepository {
    private val url = "https://dog.ceo/api/"

    private val itemService: DogService
    val itemsLiveData: MutableLiveData<ArrayList<DogData>> = MutableLiveData<ArrayList<DogData>>()
    val items: ArrayList<DogData> = ArrayList<DogData>()
    var itemsSize: Int =0

    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create()).build()
        itemService = build.create(DogService::class.java)
        getPosts()
    }

    fun getPosts() {
        itemService.getAllBreeds().enqueue(object : Callback<Dog> {
            override fun onResponse(call: Call<Dog>, response: Response<Dog>) {
                val breeds: ArrayList<String>? = response.body()?.message
                itemsSize = breeds!!.size
                for(breed in breeds!!){
                    getPost(breed)
                }
            }

            override fun onFailure(call: Call<Dog>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
            }
        })

        /*
        itemService.getAllBreeds().enqueue(object : Callback<Dog> {
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
        }) */
    }

    fun getPost(breed:String){
        itemService.getBreedRandomImage(breed).enqueue(object : Callback<DogImage> {
            override fun onResponse(call: Call<DogImage>, response: Response<DogImage>) {
                if (response.isSuccessful) {
                    val imageURL = response.body()?.message
                    val dogData = DogData()
                    dogData.breed = breed
                    dogData.imageURL= imageURL!!
                    items.add(dogData)
                    Log.i("DogRepository", "dogData:$dogData")
                    if(itemsSize == items.size){
                        itemsLiveData.postValue(items)
                        errorMessageLiveData.postValue("")
                    }
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                }
            }

            override fun onFailure(call: Call<DogImage>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
            }
        })
    }
}
