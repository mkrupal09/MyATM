package com.example.atmdemo

import android.app.Application
import androidx.room.Room
import com.example.atmdemo.data.MyRoomDatabase

class MainApplication : Application() {

     lateinit var database: MyRoomDatabase

    companion object {
         lateinit var application: MainApplication
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        database = Room.databaseBuilder(
            applicationContext,
            MyRoomDatabase::class.java, "atm-database"
        ).build()
    }
}