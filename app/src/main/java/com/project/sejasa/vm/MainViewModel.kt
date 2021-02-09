package com.project.sejasa.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.domain.models.CurrentWeatherModel
import com.project.domain.usecases.CurrentWeatherUseCase
import com.project.sejasa.rx.SchedulersProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val currentWeatherUseCase: CurrentWeatherUseCase,
    private val schedulers: SchedulersProvider
) : ViewModel() {

    val shareLiveData = MutableLiveData<CurrentWeatherModel>()
    private val compositeDisposable = CompositeDisposable()

    var latitude: Double = 0.0
    var longitude: Double = 0.0

    fun getCurrentWeatherData(lat: Double, lon: Double, appId: String) {
        currentWeatherUseCase.execute(lat, lon, appId)
            .subscribeOn(schedulers.io())
            .subscribe({
                it?.let {
                    shareLiveData.postValue(it)
                }
            },{
                it.stackTrace
            }).let {
                compositeDisposable.add(it)
            }
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }

}