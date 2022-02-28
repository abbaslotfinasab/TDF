package com.utechia.tdf.main

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.utechia.domain.enum.MainEnum

class StepsCountWorker(val appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {

    private var prefs:SharedPreferences? = null

    companion object{
        const val STEPS_COUNT = "steps"
    }

    override fun doWork(): Result {
        prefs = appContext.getSharedPreferences(MainEnum.Tdf.main, AppCompatActivity.MODE_PRIVATE)
        prefs?.edit()?.remove(STEPS_COUNT)?.apply()

        return Result.success()
    }
}