package com.irfan.finalprojectmade.sharedpreferences

import android.content.Context
import android.util.Log

class NotificationSharedpreference (context: Context){

    companion object{
        private const val PREFS_NAME = "notification_shared"
        private const val TAG_CONDITION_RELEASE = "notification_release"
        private const val TAG_CONDITION_REMINDER = "notification_reminder"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val editor = preferences.edit()

    fun setDataReminder(check: Boolean){
        editor.putBoolean(TAG_CONDITION_REMINDER, check)
        Log.e("datasprem", check.toString())
        editor.apply()
    }

    fun setDataRelease(check: Boolean){
        editor.putBoolean(TAG_CONDITION_RELEASE, check)
        Log.e("datasprel", check.toString())
        editor.apply()
    }

    fun getDataReminder(): Boolean{
        val checkData = preferences.getBoolean(TAG_CONDITION_REMINDER, false)
        return checkData
    }

    fun getDataRelease(): Boolean{
        val checkData = preferences.getBoolean(TAG_CONDITION_RELEASE, false)
        return checkData
    }
}