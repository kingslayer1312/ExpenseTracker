package com.hrishi.spendora.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrishi.spendora.repository.ExpenseEvent
import com.hrishi.spendora.repository.ExpenseState
import com.hrishi.spendora.ui.theme.green
import com.hrishi.spendora.ui.theme.greenDark
import com.hrishi.spendora.ui.theme.greenLighter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseDialog(
    state: ExpenseState,
    onEvent: (ExpenseEvent) -> Unit,
) {
    AlertDialog(
        containerColor = green,

        onDismissRequest = {
            onEvent(ExpenseEvent.HideDialog)
        },
        title = { Text("New Expense", color = Color.White, fontWeight = FontWeight.Bold) },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = greenLighter,
                    ),
                    value = state.expenseName,
                    onValueChange = {
                        onEvent(ExpenseEvent.setExpenseName(it))
                    },
                    placeholder = {
                        Text(text = "expense name")
                    },
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
                )

                var isExpanded by remember {
                    mutableStateOf(false)
                }

                ExposedDropdownMenuBox(
                    expanded = isExpanded,
                    onExpandedChange = { newValue ->
                        isExpanded = newValue
                    }
                ) {

                    TextField (
                        value = state.expenseType,
                        onValueChange = {
                            onEvent(ExpenseEvent.setExpenseName(it))
                        },
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                        },placeholder = {
                            Text(text = "expense type")
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = greenLighter,
                        ),
                        modifier = Modifier.menuAnchor()
                    )
                    
                    ExposedDropdownMenu(
                        expanded = isExpanded,
                        onDismissRequest = {
                            isExpanded = false
                        },
                        modifier = Modifier
                            .background(greenLighter)
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(text = "Entertainment", fontSize = 15.sp)
                            },
                            onClick = {
                                onEvent(ExpenseEvent.setExpenseType("Entertainment"))
                                isExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(text = "Food", fontSize = 15.sp)
                            },
                            onClick = {
                                onEvent(ExpenseEvent.setExpenseType("Food"))
                                isExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(text = "Transport", fontSize = 15.sp)
                            },
                            onClick = {
                                onEvent(ExpenseEvent.setExpenseType("Transport"))
                                isExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(text = "Utilities", fontSize = 15.sp)
                            },
                            onClick = {
                                onEvent(ExpenseEvent.setExpenseType("Utilities"))
                                isExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(text = "Miscellaneous", fontSize = 15.sp)
                            },
                            onClick = {
                                onEvent(ExpenseEvent.setExpenseType("Miscellaneous"))
                                isExpanded = false
                            }
                        )
                    }

                }

                TextField(
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = greenLighter,
                    ),
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
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = greenLighter,
                    contentColor = greenDark
                )
            ) {
                Text("Save")
            }
        }
    )
}