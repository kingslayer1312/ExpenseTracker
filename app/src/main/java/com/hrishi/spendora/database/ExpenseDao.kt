package com.hrishi.spendora.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Upsert
    suspend fun upsertExpense(expense : Expense)
    @Delete
    suspend fun deleteExpense(expense : Expense)
    @Update
    suspend fun updateExpense(expense: Expense)
    @Query("SELECT * FROM expense ORDER BY expense_amount ASC")
    fun getExpensesAscending() : Flow<List<Expense>>
    @Query("SELECT * FROM expense ORDER BY expense_amount DESC")
    fun getExpensesDescending() : Flow<List<Expense>>
}