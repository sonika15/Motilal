package com.motilal.project.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class TrendingModel(

    @ColumnInfo(name = "name")
    @SerializedName("name") val name: String?,
    @ColumnInfo(name = "full_name")
    @SerializedName("full_name") val fullName: String?,
    @ColumnInfo(name = "private")
    @SerializedName("private") val privateRepo: Boolean?,
    @ColumnInfo(name = "description")
    @SerializedName("description") val description: String?,
    val default_branch: String?,
    val watchers: String?,
    val clone_url: String?
) {
    @PrimaryKey(autoGenerate = true)
    var uId: Int =0


}