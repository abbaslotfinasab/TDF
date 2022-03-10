package com.utechia.tdf.main

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.utechia.domain.enum.MainEnum

class ResetStepsCountWorker(val appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {

    private var prefs:SharedPreferences? = null
    private var previousSteps = 0

    companion object{
        const val TOTAL_STEPS = "total_Steps"
        const val PREVIOUS_STEPS = "previous_Steps"
    }

    override fun doWork(): Result {

        prefs = applicationContext.getSharedPreferences(MainEnum.Tdf.main, AppCompatActivity.MODE_PRIVATE)
        previousSteps = prefs?.getInt(TOTAL_STEPS,0)?:0

        with(prefs?.edit()) {
            this?.putInt(PREVIOUS_STEPS,previousSteps)
        }?.apply()

        return Result.success()
    }
}