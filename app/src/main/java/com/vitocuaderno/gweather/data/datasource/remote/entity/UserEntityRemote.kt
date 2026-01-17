package com.vitocuaderno.gweather.data.datasource.remote.entity

import com.google.gson.annotations.SerializedName

data class UserEntityRemote(
    @SerializedName("email")
    val email: String,
    @SerializedName("display_name")
    val displayName: String?,
    @SerializedName("photo_url")
    val photoUrl: String?,
)
