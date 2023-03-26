package com.dog.dogsapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dog.dogsapp.network.DogImageRepository

class DogImagesViewModel : ViewModel() {
    private val repository = DogImageRepository()
    val itemsLiveData: LiveData<ArrayList<String>> = repository.itemsLiveData
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData

    init {
        //reload()
    }

    fun reload(breedName: String) {
        repository.getPosts(breedName)
    }

    operator fun get(index: Int): String? {
        return itemsLiveData.value?.get(index)
    }

}