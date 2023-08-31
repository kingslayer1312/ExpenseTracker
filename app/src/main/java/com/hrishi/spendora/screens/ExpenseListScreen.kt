package com.hrishi.spendora.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.hrishi.spendora.repository.ExpenseEvent
import com.hrishi.spendora.repository.ExpenseState
import com.hrishi.spendora.repository.SortType

var counter = 0
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseListScreen(
    navController: NavHostController,
    state: ExpenseState,
    onEvent: (ExpenseEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(ExpenseEvent.ShowDialog)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add expense"
                )
            }
        }
    ) {padding ->

        if (state.isAddingExpense) {
            AddExpenseDialog(state = state, onEvent = onEvent)
        }

        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillParentMaxWidth()
                ) {
                    Text("Spendora")
                    Text(
                        text = "sort",
                        modifier = Modifier
                            .clickable {
                                if (counter % 2 == 0) {
                                    onEvent(ExpenseEvent.SortExpenses(SortType.ASCENDING))
                                }
                                else {
                                    onEvent(ExpenseEvent.SortExpenses(SortType.DESCENDING))
                                }
                                counter += 1
                            }
                    )
                }
            }

            items(state.expenses) { expense ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = expense.expenseName)
                        Text(text = expense.expenseType)
                        Text(text = expense.expenseAmount.toString())
                    }
                    IconButton(onClick = {
                        onEvent(ExpenseEvent.DeleteExpense(expense))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete contact"
                        )
                    }
                }
            }
        }
    }
}