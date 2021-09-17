package com.motilal.project.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.motilal.project.model.TrendingModel

@Database(entities = arrayOf(TrendingModel::class), version = 1,exportSchema = false)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun trendingDao(): TrendingDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}