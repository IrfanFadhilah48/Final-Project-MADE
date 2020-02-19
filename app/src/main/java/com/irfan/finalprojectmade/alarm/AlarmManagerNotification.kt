package com.irfan.finalprojectmade.alarm

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.irfan.finalprojectmade.R
import com.irfan.finalprojectmade.model.Movie
import com.irfan.finalprojectmade.network.NotificationCallback
import com.irfan.finalprojectmade.repository.MovieRepository
import java.text.SimpleDateFormat
import java.util.*


class AlarmManagerNotification: BroadcastReceiver(), NotificationCallback {

    companion object{
        const val TYPE_DAILY_REMINDER = "daily_reminder"
        const val TYPE_DAILY_RELEASE = "daily_release"
        const val EXTRA_TYPE = "type"
        const val ID_DAILY_REMINDER = 100
        const val ID_DAILY_RELEASE = 101
        private const val MAX_NOTIFICATION = 3
    }

    private var movies = arrayListOf<Movie>()
    private var movieRepository = MovieRepository()
    private var idNotification = 0

    override fun onReceive(context: Context, intent: Intent) {
        val type = intent.getStringExtra(EXTRA_TYPE)
        Log.e("datanyaonReceive", type!!)
        if (type == TYPE_DAILY_REMINDER){
            showAlarmReminderNotification(context, "Reminder Daily", type, 1)
        }
        else if(type == TYPE_DAILY_RELEASE){
            callDataRelease(context)
        }
    }

    fun notificationTime(type: String): Calendar{
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, if (type == TYPE_DAILY_REMINDER) 7 else 8)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        return calendar
    }

    fun notificationIntent(context: Context, type: String): Intent{
        val intent = Intent(context, AlarmManagerNotification::class.java)
        intent.putExtra(EXTRA_TYPE, type)
        return intent
    }

    fun setDailyReminder(context: Context){
        val pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY_REMINDER, notificationIntent(context,
            TYPE_DAILY_REMINDER), 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, notificationTime(
            TYPE_DAILY_REMINDER).timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
        Toast.makeText(context, "Berhasil setreminder testing", Toast.LENGTH_SHORT).show()
    }

    fun cancelDailyReminder(context: Context){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmManagerNotification::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY_REMINDER, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, "Berhasil cancel reminder testing", Toast.LENGTH_SHORT).show()
    }

    fun setDailyRelease(context: Context){
        val pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY_RELEASE, notificationIntent(context, TYPE_DAILY_RELEASE), 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, notificationTime(
            TYPE_DAILY_RELEASE).timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
        Toast.makeText(context, "Berhasil setrelease testing", Toast.LENGTH_SHORT).show()
    }

    fun cancelDailyRelease(context: Context){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmManagerNotification::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY_RELEASE, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, "Berhasil cancel release testing", Toast.LENGTH_SHORT).show()
    }

    private fun callDataRelease(context: Context) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = Date()
        val dateString = sdf.format(date)
        movieRepository.getRelease(dateString, dateString, this, context)

//        Log.e("datanyaContextBefore", context.toString())
//        showAlarmReleaseNotification("tester", "contentTester", "aaaaaaa", 0 + 2, context)
//        val CHANNEL_ID = "Channel_02"
//        val CHANNEL_NAME = "Alarm_Release_channel"
//
//        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        val largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.no_preview)
//        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
//            .setSmallIcon(R.drawable.ic_favorite_border_black_24dp)
//            .setContentTitle("tester")
//            .setContentText("content tester")
//            .setLargeIcon(largeIcon)
//            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
//            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
//            .setSound(alarmSound)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(CHANNEL_ID,
//                CHANNEL_NAME,
//                NotificationManager.IMPORTANCE_DEFAULT)
//            channel.enableVibration(true)
//            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
//            builder.setChannelId(CHANNEL_ID)
//            notificationManagerCompat.createNotificationChannel(channel)
//        }
//        val notification = builder.build()
//        notificationManagerCompat.notify(2, notification)
    }

    private fun showAlarmReminderNotification(context: Context, title: String, message: String, notifId: Int) {
        val CHANNEL_ID = "Channel_01"
        val CHANNEL_NAME = "Alarm_Reminder_channel"
        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.no_preview)
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_favorite_border_black_24dp)
            .setContentTitle(title)
            .setContentText(message)
            .setLargeIcon(largeIcon)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(CHANNEL_ID)
            notificationManagerCompat.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManagerCompat.notify(notifId, notification)
    }

    private fun showAlarmReleaseNotification(title: String, overview: String, posterPath: String, position: Int, context: Context?) {
        val CHANNEL_ID = "Channel_02"
        val CHANNEL_NAME = "Alarm_Release_channel"
        Log.e("datanyaRelease", "masuk$position")
        Log.e("datanyaContextAfter", context.toString())

        val notificationManagerCompat = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        val imageLoader = Glide.with(context)
//            .asBitmap()
//            .diskCacheStrategy(DiskCacheStrategy.DATA)
//            .load(posterPath)
//            .submit()
//        val bitmap = imageLoader.get()

//        val largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.no_preview)
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_favorite_border_black_24dp)
            .setContentTitle(title)
            .setContentText(overview)
//            .setLargeIcon(bitmap)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(CHANNEL_ID)
            notificationManagerCompat.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManagerCompat.notify(position, notification)
    }


    override fun onSucess(context: Context?) {
        movies.addAll(movieRepository.releaseData)
        Log.e("datanyaAwalNotif", movies.toString())
        for (data in movies.indices){
            //TODO :Sedangkan kalau dibuat seperti ini function nya seperti tidak ke trigger
            Log.e("datanyaperulangan", data.toString())
            showAlarmReleaseNotification(movies[data].title, movies[data].overview, movies[data].posterPath!!, data + 2, context)
        }
    }

    override fun onError(message: String?) {

    }
}
