package com.motilal.project.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.motilal.project.database.SampleRepo
import com.motilal.project.model.TrendingItems
import com.motilal.project.model.TrendingModel
import com.motilal.project.viewmodel.TrendingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class MyService : Service() {
    private val timer: Timer = Timer()
    var repos: List<TrendingModel>? = null
    var ddd: LiveData<List<TrendingModel>>? = null


    var mContext: Context = this
    override fun onCreate() {
        super.onCreate()

    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("service", "started")
        val ha = Handler()
        ha.postDelayed(object : Runnable {
            override fun run() {
                //call function
                getData()
                ha.postDelayed(this, 900000)
            }
        }, 900000)
        return START_NOT_STICKY
    }

    fun getData() {
        GlobalScope.launch {
            if (checkForInternet(mContext) == true) {
                repos = getTrendingRepo()?.trendingList
                for (trendingModel in getTrendingRepo()?.trendingList!!) {
                    val trendingModel = TrendingModel(
                        trendingModel.name,
                        trendingModel.fullName,
                        trendingModel.privateRepo,
                        trendingModel.description,
                        trendingModel.default_branch,
                        trendingModel.watchers,
                        trendingModel.clone_url
                    )
                    insertData(mContext, trendingModel)
                }

            }
        }

    }


    suspend fun getTrendingRepo(): TrendingItems? {
        return withContext(Dispatchers.IO) {
            com.motilal.project.utils.NetworkCall().getTrendingRepo().body()
        }
    }

    fun insertData(context: Context, trendingModel: TrendingModel) {
        GlobalScope.launch {
            SampleRepo.insertData(context, trendingModel)

        }
    }

    private fun checkForInternet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        Log.d("tag", "TASK REMOVED")
    }

    override fun onDestroy() {
        super.onDestroy()
        val broadcastIntent = Intent()
        broadcastIntent.action = "restartservice"
        broadcastIntent.setClass(this, BroadcastReceiver::class.java)
        this.sendBroadcast(broadcastIntent)
    }

}