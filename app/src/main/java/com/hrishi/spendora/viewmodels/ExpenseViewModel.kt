package com.hrishi.spendora.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrishi.spendora.database.Expense
import com.hrishi.spendora.database.ExpenseDao
import com.hrishi.spendora.repository.ExpenseEvent
import com.hrishi.spendora.repository.ExpenseState
import com.hrishi.spendora.repository.SortType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExpenseViewModel(
    private val dao : ExpenseDao
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortType.DEFAULT)
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _expenses = _sortType
        .flatMapLatest { sortType ->
            when (sortType) {
                SortType.ASCENDING -> dao.getExpensesAscending()
                SortType.DESCENDING -> dao.getExpensesDescending()
                SortType.DEFAULT -> dao.getExpenses()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(ExpenseState())
    val state = combine(_state, _sortType, _expenses) { state, sortType, expenses ->
        state.copy(
            expenses = expenses,
            sortType = sortType,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ExpenseState())

    fun onEvent(event : ExpenseEvent) {
        when (event) {
            is ExpenseEvent.DeleteExpense -> {
                viewModelScope.launch {
                    dao.deleteExpense(event.expense)
                }
            }
            ExpenseEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingExpense = false
                ) }
            }
            ExpenseEvent.SaveExpense -> {
                val expenseName = state.value.expenseName
                val expenseType = state.value.expenseType
                val expenseAmount = state.value.expenseAmount

                if (
                    expenseName.isBlank() ||
                    expenseType.isBlank() ||
                    expenseAmount == 0
                    ) {
                    return
                }

                val expense = Expense(
                    expenseName = expenseName,
                    expenseType = expenseType,
                    expenseAmount = expenseAmount
                )
                viewModelScope.launch {
                    dao.insertExpense(expense)
                }
                _state.update {
                        it.copy(
                            isAddingExpense = false,
                            expenseName = "",
                            expenseType = "",
                            expenseAmount = 0
                        )
                }
            }
            ExpenseEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingExpense = true
                ) }
            }
            is ExpenseEvent.SortExpenses -> {
                _sortType.value = event.sortType
            }
            is ExpenseEvent.setExpenseAmount -> {
                _state.update { it.copy(
                    expenseAmount = event.expenseAmount.toInt()
                ) }
            }
            is ExpenseEvent.setExpenseName -> {
                _state.update { it.copy(
                    expenseName = event.expenseName
                ) }
            }
            is ExpenseEvent.setExpenseType -> {
                _state.update { it.copy(
                    expenseType = event.expenseType
                ) }
            }
        }
    }

}