package com.dog.dogsapp.network

import java.io.Serializable

data class Dog(val message: ArrayList<String>, val status: String) : Serializable {
}

data class DogImage(val message: String, val status: String) : Serializable {
}

class DogData{
    var breed: String = "defaultvalue"
        get() = field                     // getter
        set(value) { field = value }      // setter

    var imageURL: String = "defaultvalue"
        get() = field                     // getter
        set(value) { field = value }      // setter

    override fun toString(): String {
        return imageURL
    }
}
