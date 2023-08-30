package com.hrishi.spendora.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hrishi.spendora.screens.ExpenseItemScreen
import com.hrishi.spendora.screens.ExpenseListScreen
import com.hrishi.spendora.screens.HomeScreen

@Composable
fun AppNavigation(startDestination: String = "home") {
    val navController = rememberNavController()

    NavHost(navController, startDestination = startDestination) {
        composable("home") {
            HomeScreen(navController)
        }
        composable("expense-item") {
            ExpenseItemScreen(navController)
        }
        composable("expense-list") {
            ExpenseListScreen(navController)
        }
    }
}
