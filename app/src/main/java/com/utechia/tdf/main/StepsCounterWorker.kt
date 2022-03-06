package com.utechia.tdf.main

import android.content.Context
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class StepsCounterWorker(val appContext: Context, workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams),SensorEventListener {

    private var prefs:SharedPreferences? = null
    private var totalSteps = 0
    private var sensorManager :SensorManager? = null
    private var  sensor : Sensor? = null


    companion object{
        const val TOTAL_STEPS = "total_Steps"

    }

    override suspend fun doWork(): Result {

        sensorManager = applicationContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST)

        return Result.success()
    }

    override fun onSensorChanged(event: SensorEvent?) {

        totalSteps = event?.values?.get(0)?.toInt()?:0

        with(prefs?.edit()) {
            this?.putInt(TOTAL_STEPS, totalSteps)
        }?.apply()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}