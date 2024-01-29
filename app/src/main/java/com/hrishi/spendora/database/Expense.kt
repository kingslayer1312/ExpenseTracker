package com.hrishi.spendora.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val expenseID : Int = 0,
    @ColumnInfo(name = "expense_name")
    var expenseName : String,
    @ColumnInfo(name = "expense_type")
    var expenseType : String,
    @ColumnInfo(name = "expense_amount")
    var expenseAmount : Int
)
