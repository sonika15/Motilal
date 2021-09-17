package com.motilal.project.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.motilal.project.model.TrendingModel

@Dao
interface TrendingDao {

    @Query("SELECT * FROM TrendingModel")
    fun getAll(): LiveData<List<TrendingModel>>

    @Insert
    fun insertAll(trendingModel: TrendingModel)
}