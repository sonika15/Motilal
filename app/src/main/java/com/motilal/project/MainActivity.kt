package com.motilal.project

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.motilal.project.adapter.TrendingAdapter
import com.motilal.project.model.TrendingModel
import com.motilal.project.service.BroadcastReceiver
import com.motilal.project.service.MyService
import com.motilal.project.viewmodel.TrendingViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    lateinit var viewModel: TrendingViewModel
    lateinit var rvList: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage("Loading")
        progressDialog.show()

        startService(Intent(applicationContext, MyService::class.java)) // background service

        //check internet connection
        if (checkForInternet(this)) {
            rvList = findViewById(R.id.rvList);
            viewModel = ViewModelProvider(this).get(TrendingViewModel::class.java)
            viewModel.isNetwork(true)
            viewModel.repos?.observe(this@MainActivity, Observer {
                if (it == null) {
                    progressDialog.dismiss()
                    Toast.makeText(this, "Network Error occur.", Toast.LENGTH_LONG).show();

                } else {
                    progressDialog.dismiss()
                    val res: List<TrendingModel> = it
                    val adapter: TrendingAdapter = TrendingAdapter(res, this@MainActivity)
                    val layoutManager = LinearLayoutManager(applicationContext)
                    rvList.layoutManager = layoutManager
                    rvList.itemAnimator = DefaultItemAnimator()
                    rvList.adapter = adapter
                    for (trendingModel in it) {
                        val trendingModel = TrendingModel(
                            trendingModel.name,
                            trendingModel.fullName,
                            trendingModel.privateRepo,
                            trendingModel.description,
                            trendingModel.default_branch,
                            trendingModel.watchers,
                            trendingModel.clone_url
                        )
                        CoroutineScope(Dispatchers.Main).launch {
                            viewModel.insertData(this@MainActivity, trendingModel)
                        }
                    }
                }
            })
        } else {
            rvList = findViewById(R.id.rvList);
            viewModel = ViewModelProvider(this).get(TrendingViewModel::class.java)
            viewModel.isNetwork(false)
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.getLoginDetails(this@MainActivity)!!
                    .observe(this@MainActivity, Observer {
                        if (it == null) {
                            Log.e("Tag", "Data Not Found")
                        } else {
                            progressDialog.dismiss()
                            Log.e("Tag", "Data Found")
                            val res: List<TrendingModel> = it
                            val adapter: TrendingAdapter = TrendingAdapter(res, this@MainActivity)
                            val layoutManager = LinearLayoutManager(applicationContext)
                            rvList.layoutManager = layoutManager
                            rvList.itemAnimator = DefaultItemAnimator()
                            rvList.adapter = adapter
                        }

                    })


            }
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

    override fun onDestroy() {
        val broadcastIntent = Intent()
        broadcastIntent.action = "restartservice"
        broadcastIntent.setClass(this, BroadcastReceiver::class.java)
        this.sendBroadcast(broadcastIntent)
        super.onDestroy()

    }

    override fun onStop() {
        Log.e("stop", "ondestroy")
        val broadcastIntent = Intent()
        broadcastIntent.action = "restartservice"
        broadcastIntent.setClass(this, BroadcastReceiver::class.java)
        this.sendBroadcast(broadcastIntent)
        super.onStop()
    }
}