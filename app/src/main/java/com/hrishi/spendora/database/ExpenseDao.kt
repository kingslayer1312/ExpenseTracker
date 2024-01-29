package com.hrishi.spendora.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExpense(expense : Expense)

    @Delete
    fun deleteExpense(expense : Expense)

    @Query("SELECT * FROM expense")
    fun getExpenses() : Flow<List<Expense>>

    @Query("SELECT * FROM expense ORDER BY expense_amount ASC")
    fun getExpensesAscending() : Flow<List<Expense>>

    @Query("SELECT * FROM expense ORDER BY expense_amount DESC")
    fun getExpensesDescending() : Flow<List<Expense>>
}
