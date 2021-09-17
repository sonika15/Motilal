package com.motilal.project.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motilal.project.database.SampleRepo
import com.motilal.project.model.TrendingItems
import com.motilal.project.model.TrendingModel
import com.motilal.project.utils.NetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrendingViewModel : ViewModel() {
    var repos: LiveData<List<TrendingModel>>? = MutableLiveData()
    var isInternet: Boolean? = null

    fun isNetwork(boolean: Boolean) {
        isInternet = boolean
        Log.e("model", isInternet.toString())
        viewModelScope.launch {
            if (isInternet == true) {
                try {
                    val mutableLiveData = repos as MutableLiveData
                    mutableLiveData.value = getTrendingRepo()?.trendingList
                } catch (e: Exception) {
                    Log.e("Exception", e.printStackTrace().toString())
                    e.printStackTrace()
                }

            } else {
                Log.e("Viewmodel", "no internet")
            }
        }
    }

    suspend fun getTrendingRepo(): TrendingItems? {
        return withContext(Dispatchers.IO) {
            NetworkCall().getTrendingRepo().body()
        }
    }


    fun insertData(context: Context, trendingModel: TrendingModel) {
        viewModelScope.launch {
            SampleRepo.insertData(context, trendingModel)

        }
    }


    fun getLoginDetails(context: Context): LiveData<List<TrendingModel>>? {
        repos = SampleRepo.getLoginDetails(context)
        return repos
    }

}