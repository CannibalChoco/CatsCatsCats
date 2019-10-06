package com.kglazuna.app

import android.content.Context
import android.net.Network
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.ConnectivityManager.NetworkCallback
import androidx.lifecycle.MutableLiveData
import timber.log.Timber


class ConnectionStateMonitor : NetworkCallback() {

    val networkRequest: NetworkRequest =
        NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI).build()

    var isConnected = MutableLiveData<Boolean>()

    fun register(context: Context) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(networkRequest, this)
    }

    fun unregister(context: Context) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.unregisterNetworkCallback(this)
    }

    override fun onAvailable(network: Network) {
        isConnected.postValue(true)
        Timber.d("Connectivity: onAvailable")
    }

    override fun onUnavailable() {
        isConnected.postValue(false)
        Timber.d("Connectivity: onUnavailable")
    }

    override fun onLost(network: Network?) {
        isConnected.postValue(false)
        Timber.d("Connectivity: onLost")
    }

    companion object {
        lateinit var INSTANCE: ConnectionStateMonitor
            private set

        fun createInstance() {
            INSTANCE = ConnectionStateMonitor()
        }
    }
}
