package com.motilal.project.model

import androidx.lifecycle.LiveData
import com.google.gson.annotations.SerializedName

data class TrendingItems(@SerializedName("items") var trendingList: List<TrendingModel>) {

}