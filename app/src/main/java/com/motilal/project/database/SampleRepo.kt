package com.motilal.project.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.motilal.project.model.TrendingModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class SampleRepo {
    companion object {

        var loginDatabase: AppDatabase? = null

        var loginTableModel: LiveData<List<TrendingModel>>? = null
        var serviceModel: List<TrendingModel>? = null

        fun initializeDB(context: Context) : AppDatabase {
            return AppDatabase.getDatabase(context)
        }

        fun insertData(context: Context, trendingModel: TrendingModel) {

            loginDatabase = initializeDB(context)

            CoroutineScope(IO).launch {
                val loginDetails = trendingModel
                loginDatabase!!.trendingDao().insertAll(loginDetails)
            }

        }

        fun getLoginDetails(context: Context) : LiveData<List<TrendingModel>>? {
            loginDatabase = initializeDB(context)
            loginTableModel = loginDatabase!!.trendingDao().getAll()

            return loginTableModel
        }

    }
}