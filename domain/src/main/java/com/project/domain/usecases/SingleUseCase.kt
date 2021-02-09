package com.project.domain.usecases

import io.reactivex.Single

interface SingleUseCase<R> {
    fun execute(lat: Double, lon: Double, appId: String): Single<R>
}