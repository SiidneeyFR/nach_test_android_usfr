package com.example.nach_test

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.domain.locations.model.Location
import com.example.nach_test.databinding.ActivityMainBinding
import com.example.nach_test.presenation.main.MainContract
import com.example.nach_test.presenation.main.MainPresenter
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.google.firebase.firestore.FirebaseFirestore
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainContract.View, HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var mainPresenter: MainContract.Presenter

    val db = FirebaseFirestore.getInstance()

    private val resultRequestPermission = 9999
    private var isReturnSettings = false
    private val minutes = 60000*5L

    private var locationManager : LocationManager? = null
    private var mLocationRequest: LocationRequest? = null

    //iniciar navegación, solicitar permiso de ubicación
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_movies, R.id.navigation_locations, R.id.navigation_images))
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissionLocation()
        } else {
            startServiceLocation()
        }
    }

    //mostrar notifcaciones de ubicación
    override fun showNotifcation(location: Location) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val contentNotification = getString(R.string.activity_main_notification_content, location.lat.toString(), location.lon.toString())

        val notification = NotificationCompat.Builder(this, "0")
            .setDefaults(Notification.DEFAULT_ALL)
            .setSmallIcon(R.drawable.ic_map_multi)
            .setContentTitle(getString(R.string.activity_main_notification_title))
            .setContentText(contentNotification)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "Your_channel_id"
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
            notification.setChannelId(channelId)
        }

        notificationManager.notify(0, notification.build())
    }

    // validar permisos de ubicación, si no tiene solicitar, caso contrrar iniciar obtener ubicación
    private fun requestPermissionLocation() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED  &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED-> {
                startServiceLocation()
            }
            else -> {
                val permissionArray = arrayListOf(Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(permissionArray.toTypedArray(), resultRequestPermission)
                }
            }
        }
    }

    // validar si lo permisos fueron obtenidos
    // si -  iniciar a obtener ubicacion
    // no - mostrar alerta de que los permisos son necesarios
    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == resultRequestPermission) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                mainPresenter.getLocations()
            } else {
                showMessageNoPermisions()
            }
        }
    }

    // alerta de permisos son necesarios, opciones de ir a jsutes o salir
    private fun showMessageNoPermisions() {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setMessage(getString(R.string.acitivy_main_permisions))
        alertDialog.setCancelable(false)
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.acitivy_main_go_settings)) { _, _ ->
            val intent = Intent( Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts(
                "package", packageName, null))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            isReturnSettings = true
        }
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.acitivy_main_cancel)) { _, _ ->
            onBackPressed()
        }
        alertDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.onDestroy()
    }

    //validar que despues de ir ajustes lo permisos fueron activados
    override fun onResume() {
        super.onResume()
        if (isReturnSettings) {
            isReturnSettings = false
           requestPermissionLocation()
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    override fun setPresenter(presenter: MainPresenter) {
        this.mainPresenter = presenter
    }

    //iniciar a obtner la ubicación, en callback guardar, detener 5min
    @SuppressLint("MissingPermission")
    private fun startServiceLocation() {
        getLocation(object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                saveLocation(p0.lastLocation)
                getFusedLocationProviderClient(applicationContext).removeLocationUpdates(this)
                Handler().postDelayed({
                    startServiceLocation()
                }, minutes)
            }

            override fun onLocationAvailability(p0: LocationAvailability) {
                super.onLocationAvailability(p0)
            }
        })
    }

    //iniciar provedor de ubicación
    @SuppressLint("MissingPermission")
    fun getLocation(locationCallback: LocationCallback){
        try {
            val UPDATE_INTERVAL = minutes
            val FASTEST_INTERVAL: Long = 4000
            val expirationTime: Long = 30000


            mLocationRequest = LocationRequest()
            mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            mLocationRequest!!.interval = UPDATE_INTERVAL
            mLocationRequest!!.fastestInterval = FASTEST_INTERVAL
            mLocationRequest!!.setExpirationDuration(expirationTime)

            val builder = LocationSettingsRequest.Builder()
            builder.addLocationRequest(mLocationRequest!!)
            val locationSettingsRequest = builder.build()

            val settingsClient = LocationServices.getSettingsClient(this)
            settingsClient.checkLocationSettings(locationSettingsRequest)

            getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest!!, locationCallback,
                Looper.myLooper()!!
            )
        }catch (e: Exception){
            Log.e("getLocation","no location");
        }
    }

    //guardar ubicacion en firebase firestore
    private fun saveLocation(location: android.location.Location) {
        val locationMap = hashMapOf(
            "lat" to location.latitude,
            "lon" to location.longitude,
            "date" to getCurrentDate()
        )

        db.collection("locations")
            .add(locationMap)
            .addOnSuccessListener {
                    documentReference ->
                showNotifcation(Location(location.latitude, location.longitude))
                Log.e("db", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener  { e ->
                Log.e("db", "Error adding document", e)
            }
    }

    //obtener la fecha para guadar la ubicación
    private fun getCurrentDate(): String {
        val currentTime = System.currentTimeMillis()
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = currentTime
        val dateFormat = SimpleDateFormat("yyy-MM-dd")
        return dateFormat.format(calendar.time)
    }

}