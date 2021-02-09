package com.project.data.mappers

import com.project.data.models.CurrentWeatherResponse
import com.project.domain.models.CurrentWeatherModel
import javax.inject.Inject

class ShareMapper @Inject constructor() {

    fun toDataModel(response: CurrentWeatherResponse): CurrentWeatherModel {
        return CurrentWeatherModel(
            id = response.id,
            name = response.name,
            cod = response.cod
        )
    }

}