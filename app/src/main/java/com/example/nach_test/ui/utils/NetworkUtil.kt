package com.example.nach_test.ui.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class NetworkUtil {
    val TYPE_WIFI = 1
    val TYPE_MOBILE = 2
    val TYPE_NOT_CONNECTED = 0

    //obtener si hay conexión a intener por wifi o datos
    private fun getConnectivityStatus(context: Context): Int {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw      = connectivityManager.activeNetwork ?: return TYPE_NOT_CONNECTED
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return TYPE_NOT_CONNECTED
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> TYPE_WIFI
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> TYPE_MOBILE
                else -> TYPE_NOT_CONNECTED
            }
        } else {
            val nwInfoWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            val nwInfoMobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            return when {
                nwInfoWifi?.isConnected == true -> TYPE_WIFI
                nwInfoMobile?.isConnected == true -> TYPE_MOBILE
                else -> TYPE_NOT_CONNECTED
            }
        }
    }

    //validar si hay conexón a internet
    fun haveInternet(context: Context): Boolean {
        val conn: Int = getConnectivityStatus(context)
        return when (conn) {
            TYPE_WIFI, TYPE_MOBILE -> {
                true
            }
            else -> false
        }
    }
}