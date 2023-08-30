package com.hrishi.spendora.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Expense(
    var expenseName : String,
    var expenseType : String,
    var expenseAmount : Int,
    @PrimaryKey(autoGenerate = true)
    val expenseID : Int = 0
)
