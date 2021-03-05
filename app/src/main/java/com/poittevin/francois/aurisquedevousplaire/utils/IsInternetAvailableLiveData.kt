package com.poittevin.francois.aurisquedevousplaire.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.lifecycle.LiveData

class IsInternetAvailableLiveData(context: Context) : LiveData<Boolean>() {

    private var connectivityManager: ConnectivityManager? = null
    private var networkRequestBuilder: NetworkRequest.Builder? = null
    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    init {
        postValue(false)

        connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        networkRequestBuilder = NetworkRequest.Builder()
        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                postValue(true)
            }

            override fun onLost(network: Network) {
                postValue(false)
            }
        }
    }

    override fun onActive() {
        super.onActive()
        connectivityManager!!.registerNetworkCallback(
            networkRequestBuilder!!.build(),
            networkCallback!!
        )
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager!!.unregisterNetworkCallback(networkCallback!!)
    }
}