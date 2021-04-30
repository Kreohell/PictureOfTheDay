package ru.geekbrains.pictureoftheday.network.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.geekbrains.pictureoftheday.network.response.PODServerResponseData

interface PODapi {
    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Call<PODServerResponseData>
}