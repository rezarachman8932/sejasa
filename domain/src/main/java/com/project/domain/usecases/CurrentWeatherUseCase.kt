package com.project.domain.usecases

import com.project.domain.models.CurrentWeatherModel
import com.project.domain.repositories.RemoteRepo
import io.reactivex.Single
import javax.inject.Inject

class CurrentWeatherUseCase @Inject constructor(private val apiRepo: RemoteRepo) :
        SingleUseCase<CurrentWeatherModel> {

    override fun execute(lat: Double, lon: Double, appId: String): Single<CurrentWeatherModel> = apiRepo.getCurrentWeather(lat, lon, appId)

}