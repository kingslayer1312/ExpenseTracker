package com.hrishi.spendora.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.hrishi.spendora.MainActivity
import com.hrishi.spendora.repository.ExpenseEvent
import com.hrishi.spendora.repository.ExpenseState

@Composable
fun HomeScreen(
    navController: NavHostController,
    state: ExpenseState,
    onEvent: (ExpenseEvent) -> Unit
) {
    Button(onClick = {
        navController.navigate("expense-list")
    }) {
        Text("Click")
    }
}