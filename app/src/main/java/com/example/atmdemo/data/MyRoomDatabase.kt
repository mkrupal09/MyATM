package com.example.atmdemo.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Transaction::class], version = 1)
abstract class MyRoomDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}