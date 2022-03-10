package com.utechia.tdf.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.utechia.domain.enum.NotificationEnum
import com.utechia.tdf.R

class StepsCounterWorker(val appContext: Context, workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams),SensorEventListener {

    private var prefs: SharedPreferences? = null
    private var totalSteps = 0
    private var sensorManager: SensorManager? = null
    private var sensor: Sensor? = null


    companion object {
        const val TOTAL_STEPS = "total_Steps"

    }

    private val notificationManager =
        appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override suspend fun doWork(): Result {

        sensorManager = applicationContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST)

        return Result.success()
    }

    override fun onSensorChanged(event: SensorEvent?) {

        totalSteps = event?.values?.get(0)?.toInt() ?: 0

        createForegroundInfo(totalSteps,0f)
        with(prefs?.edit()) {
            this?.putInt(TOTAL_STEPS, totalSteps)
        }?.apply()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    private fun createForegroundInfo(steps: Int, calory: Float): ForegroundInfo {
        val title = appContext.getString(R.string.dailySteps)
        val cancel = appContext.getString(R.string.cancel)

        val intent = WorkManager.getInstance(appContext).createCancelPendingIntent(id)

        val notification =
            NotificationCompat.Builder(appContext, NotificationEnum.ChannelId.notification)
                .setContentTitle(title)
                .setTicker(title)
                .setContentText(steps.toString())
                .setSmallIcon(R.drawable.ic_tdf_notification)
                .setOngoing(true)
                .addAction(R.drawable.ic_delete, cancel, intent)
                .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val notificationChannel = NotificationChannel(
                NotificationEnum.ChannelId.notification,
                NotificationEnum.ChannelName.notification, NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0, notification)

        return ForegroundInfo(42,notification)
    }
}
