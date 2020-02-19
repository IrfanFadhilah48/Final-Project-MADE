package com.irfan.finalprojectmade.ui.reminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.irfan.finalprojectmade.R
import com.irfan.finalprojectmade.alarm.AlarmManagerNotification
import com.irfan.finalprojectmade.sharedpreferences.NotificationSharedpreference
import com.irfan.finalprojectmade.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_reminder.*
import org.jetbrains.anko.startActivity

class ReminderActivity : AppCompatActivity() {

    private lateinit var notificationsp: NotificationSharedpreference
    private lateinit var alarmManager: AlarmManagerNotification
    private var reminder = false
    private var release = false

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        startActivity<MainActivity>()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        notificationsp = NotificationSharedpreference(this)
        alarmManager = AlarmManagerNotification()

        reminder = notificationsp.getDataReminder()
        release = notificationsp.getDataRelease()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.reminder_settings)

        if (reminder) {
            checkSwitchReminder()
            Log.e("datanyaReminder", "true")
        }

        if (release) {
            checkSwitchRelease()
            Log.e("datanyaRelease", "true")
        }


        sc_reminder.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                alarmManager.setDailyReminder(this)
                checkSwitchReminder()
            }
            else{
                alarmManager.cancelDailyReminder(this)
                uncheckedSwitchReminder()
            }
        }

        sc_release.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                alarmManager.setDailyRelease(this)
                checkSwitchRelease()
            }
            else{
                release = false
                alarmManager.cancelDailyRelease(this)
                uncheckedSwitchRelease()
            }
        }
    }

    private fun checkSwitchRelease() {
        sc_release.isChecked = true
        notificationsp.setDataRelease(true)
    }

    private fun uncheckedSwitchRelease() {
        alarmManager.cancelDailyRelease(this)
    }

    private fun checkSwitchReminder() {
        sc_reminder.isChecked = true
        notificationsp.setDataReminder(true)
    }

    private fun uncheckedSwitchReminder() {
        notificationsp.setDataReminder(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
