package com.hrishi.spendora.repository

import com.hrishi.spendora.database.Expense

data class ExpenseState(
    val expenses : List<Expense> = emptyList(),
    val expenseName : String = "",
    val expenseType : String = "",
    val expenseAmount : Int = 0,
    val isAddingExpense : Boolean = false,
    val sortType: SortType = SortType.DEFAULT
)
