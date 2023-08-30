package com.hrishi.spendora.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.hrishi.spendora.model.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Upsert
    suspend fun upsertExpense(expense: Expense)
    @Delete
    suspend fun deleteExpense(expense: Expense)

    @Query("SELECT * FROM expense ORDER BY expenseAmount ASC")
    fun orderExpensesAscending() : Flow<List<Expense>>
    @Query("SELECT * FROM expense ORDER BY expenseAmount DESC")
    fun orderExpensesDescending() : Flow<List<Expense>>
}