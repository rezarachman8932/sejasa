package com.project.sejasa.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.project.sejasa.R
import com.project.sejasa.di.obtainViewModel
import com.project.sejasa.util.PermissionUtil
import com.project.sejasa.vm.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewmodel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observeCurrentWeather()

        handleButtons()
    }

    override fun onStart() {
        super.onStart()

        when {
            PermissionUtil.isAccessFineLocationGranted(this) -> {
                when {
                    PermissionUtil.isLocationEnabled(this) -> {
                        setUpLocationListener()
                    }
                    else -> {
                        PermissionUtil.showGPSNotEnabledDialog(this)
                    }
                }
            }
            else -> {
                PermissionUtil.requestAccessFineLocationPermission(
                    this,
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    when {
                        PermissionUtil.isLocationEnabled(this) -> {
                            setUpLocationListener()
                        }
                        else -> {
                            PermissionUtil.showGPSNotEnabledDialog(this)
                        }
                    }
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.location_permission_not_granted),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun setUpLocationListener() {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        val locationRequest = LocationRequest()
            .setInterval(2000)
            .setFastestInterval(2000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //TODO consider calling ActivityCompat#requestPermissions
            return
        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                for (location in locationResult.locations) {
                    viewmodel.latitude = location.latitude
                    viewmodel.longitude = location.longitude

                    val latitudeLongitude = viewmodel.latitude.toString() + " " + viewmodel.longitude.toString()
                    tv_value_current_location.text = latitudeLongitude
                }
            }
        }, Looper.myLooper())
    }

    private fun observeCurrentWeather() {
        viewmodel = viewModelFactory.obtainViewModel(this)
        viewmodel.shareLiveData.observe(this, Observer {
            it.let {
                //TODO show the result from API
            }
        })
    }

    private fun handleButtons() {
        btn_get_weather.setOnClickListener {
            viewmodel.getCurrentWeatherData(
                viewmodel.latitude,
                viewmodel.longitude,
                APP_ID
            )
        }

        btn_open_map.setOnClickListener {
            navigateToMap()
        }
    }

    private fun navigateToMap() {
        //TODO navigate to map detail
    }

    companion object {
        const val APP_ID = "fdf871cedaf3413c6a23230372c30a02"
        const val LOCATION_PERMISSION_REQUEST_CODE = 99
    }

}