package com.project.data.models

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("cod")
    val cod: Int
)