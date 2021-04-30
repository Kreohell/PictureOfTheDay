package ru.geekbrains.pictureoftheday.network.data

import ru.geekbrains.pictureoftheday.network.response.PODServerResponseData

sealed class PODData{
    data class Success(val serverResponseData: PODServerResponseData) : PODData()
    data class Error(val error: Throwable) : PODData()
    data class Loading(val progress: Int?) : PODData()
}
