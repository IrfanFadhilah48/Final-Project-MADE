package com.irfan.finalprojectmade.ui.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.irfan.finalprojectmade.R
import com.irfan.finalprojectmade.alarm.AlarmManagerNotification
import com.irfan.finalprojectmade.sharedpreferences.NotificationSharedpreference
import com.irfan.finalprojectmade.ui.reminder.ReminderActivity
import com.irfan.finalprojectmade.ui.search.SearchActivity
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    private val mClose = 2000
    private var mBackPressed: Long = 0
    private var menuSearch: Menu? = null
    private lateinit var notificationAlarm: AlarmManagerNotification
    private lateinit var notificationSP: NotificationSharedpreference
    private var checkReminder = false
    private var checkRelease = false

    override fun onBackPressed() {
        if (mBackPressed + mClose > System.currentTimeMillis()) {
            finishAffinity()
            super.onBackPressed()
            return
        } else {
            toast(resources.getString(R.string.exit_click))
        }
        mBackPressed = System.currentTimeMillis()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        notificationSP = NotificationSharedpreference(this)
        notificationAlarm = AlarmManagerNotification()
        checkReminder = notificationSP.getDataReminder()
        checkRelease = notificationSP.getDataRelease()


        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_movie,
                R.id.navigation_serialtv,
                R.id.navigation_favorite
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        menuSearch = menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.change_language -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                true
            }
            R.id.search_activity -> {
                startActivity<SearchActivity>()
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                true
            }
            R.id.notification_reminder -> {
                startActivity<ReminderActivity>()
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
