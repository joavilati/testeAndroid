package com.jhonata.catapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Breed (
    val name: String = "",
    val origin: String = "",
    val temperament: String = "",
    val description: String = "",
    val image: Image
)