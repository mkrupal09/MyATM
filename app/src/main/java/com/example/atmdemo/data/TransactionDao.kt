package com.example.atmdemo.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TransactionDao {

    @Query("SELECT * FROM `transaction` order by transactionId desc")
    fun getAll(): LiveData<List<Transaction>>

    @Insert
    suspend fun insertAll(vararg users: Transaction)
}