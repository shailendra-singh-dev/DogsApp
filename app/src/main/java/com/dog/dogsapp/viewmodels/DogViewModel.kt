package com.dog.dogsapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dog.dogsapp.network.DogData
import com.dog.dogsapp.network.DogRepository

class DogViewModel : ViewModel() {
    private val repository = DogRepository()
    val itemsLiveData: LiveData<ArrayList<DogData>> = repository.itemsLiveData
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData

    init {
        reload()
    }

    fun reload() {
        repository.getPosts()
    }

    operator fun get(index: Int): DogData? {
        return itemsLiveData.value?.get(index)
    }
}