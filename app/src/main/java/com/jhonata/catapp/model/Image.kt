package com.jhonata.catapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    private val width: Int,
    private val height: Int,
    val url: String
) {
    val aspectRatio: Double get() = width.toDouble()/height
}