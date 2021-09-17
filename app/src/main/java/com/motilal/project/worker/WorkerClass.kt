package com.motilal.project.worker

import android.content.Context
import androidx.work.*

class WorkerClass(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {


    fun enqueue() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED) // must be connected to internet
            .build()
        val request =
            OneTimeWorkRequest.Builder(this::class.java)
                .setConstraints(constraints)
                .build()
        //WorkManager.getInstance(this).enqueue(request)
    }
    override fun doWork(): Result {


        return Result.success()
    }
}