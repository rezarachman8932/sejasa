package com.project.data.repositories

import com.project.data.api.ApiService
import com.project.data.mappers.ShareMapper
import com.project.domain.models.CurrentWeatherModel
import com.project.domain.repositories.RemoteRepo
import io.reactivex.Single
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val shareMapper: dagger.Lazy<ShareMapper>
) : RemoteRepo {

    override fun getCurrentWeather(lat: Double, lon: Double, appId: String): Single<CurrentWeatherModel> {
        return apiService.getCurrentWeather(lat, lon, appId).map {
            shareMapper.get().toDataModel(it)
        }
    }

}