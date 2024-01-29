package com.hrishi.spendora.repository

import com.hrishi.spendora.database.Expense

sealed interface ExpenseEvent {
    object SaveExpense : ExpenseEvent
    data class setExpenseName(val expenseName : String) : ExpenseEvent
    data class setExpenseType(val expenseType : String) : ExpenseEvent
    data class setExpenseAmount(val expenseAmount : String) : ExpenseEvent
    object ShowDialog : ExpenseEvent
    object HideDialog : ExpenseEvent
    data class SortExpenses(val sortType : SortType) : ExpenseEvent
    data class DeleteExpense(val expense: Expense)  : ExpenseEvent

}