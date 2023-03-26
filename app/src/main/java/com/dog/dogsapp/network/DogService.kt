package com.dog.dogsapp.network

import com.dog.dogsapp.network.Dog
import com.dog.dogsapp.network.DogImage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DogService {
    @GET("breeds/list")
    fun getAllBreeds(): Call<Dog>

    @GET("breed/{breedName}/images/random")
    fun getBreedRandomImage(@Path("breedName") breedName: String): Call<DogImage>

    @GET("breed/{breedName}/images")
    fun getBreedImages(@Path("breedName") breedName: String): Call<Dog>
}