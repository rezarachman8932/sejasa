package com.project.domain.repositories

import com.project.domain.models.CurrentWeatherModel
import io.reactivex.Single

interface RemoteRepo {
    fun getCurrentWeather(lat: Double, lon: Double, appId: String): Single<CurrentWeatherModel>
}