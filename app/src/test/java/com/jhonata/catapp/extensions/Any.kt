package com.jhonata.catapp.util

import com.google.gson.Gson
import com.jhonata.catapp.extensions.fromJson

inline fun <reified T> Any.readJSON(fileName: String) = Gson().fromJson<T>(readJSONRaw(fileName))!!

fun Any.readJSONRaw(fileName: String) = javaClass.classLoader!!.getResourceAsStream("json/${if (fileName.endsWith(".json")) fileName else "$fileName.json"}").bufferedReader().use { it.readText() }
