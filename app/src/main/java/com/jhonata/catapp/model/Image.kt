package com.jhonata.catapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    private val width: Int = 0,
    private val height: Int = 0,
    val url: String = ""
) {
    val aspectRatio: Double get() = width.toDouble()/height
}