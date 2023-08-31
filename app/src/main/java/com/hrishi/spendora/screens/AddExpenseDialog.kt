package com.hrishi.spendora.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.hrishi.spendora.repository.ExpenseEvent
import com.hrishi.spendora.repository.ExpenseState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseDialog(
    state: ExpenseState,
    onEvent: (ExpenseEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {
            onEvent(ExpenseEvent.HideDialog)
        },
        title = { Text("Add contact") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.expenseName,
                    onValueChange = {
                        onEvent(ExpenseEvent.setExpenseName(it))
                    },
                    placeholder = {
                        Text(text = "expense name")
                    },
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
                )
                TextField(
                    value = state.expenseType,
                    onValueChange = {
                        onEvent(ExpenseEvent.setExpenseType(it))
                    },
                    placeholder = {
                        Text(text = "expense type")
                    },
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
                )
                TextField(
                    value = state.expenseAmount.toString(),
                    onValueChange = {
                        onEvent(ExpenseEvent.setExpenseAmount(it))
                    },
                    placeholder = {
                        Text(text = "expense amount")
                    }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onEvent(ExpenseEvent.SaveExpense)
                }
            ) {
                Text("Save")
            }
        }
    )
}



