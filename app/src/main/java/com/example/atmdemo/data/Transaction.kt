package com.example.atmdemo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Transaction(
    @PrimaryKey(autoGenerate = true) var transactionId: Int,
    var amount: Long = 0,
    var hundred: Int = 0,
    var twoHundreds: Int = 0,
    var fiveHundreds: Int = 0,
    var twoThousands: Int = 0
)