package com.hrishi.spendora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.hrishi.spendora.database.ExpenseDatabase
import com.hrishi.spendora.screens.ExpenseItemScreen
import com.hrishi.spendora.screens.ExpenseListScreen
import com.hrishi.spendora.ui.theme.SpendoraTheme
import com.hrishi.spendora.viewmodels.ExpenseViewModel

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ExpenseDatabase::class.java,
            "expenses.db"
        ).allowMainThreadQueries().build()
    }

    @Suppress("UNCHECKED_CAST")
    private val viewModel by viewModels<ExpenseViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ExpenseViewModel(db.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpendoraTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state by viewModel.state.collectAsState()
                    val navController = rememberNavController()

                    NavHost(navController, startDestination = "expense-list") {
                        composable("expense-list") {
                            ExpenseListScreen(navController, state = state, onEvent = viewModel::onEvent)
                        }
                        composable("expense-item") {
                            ExpenseItemScreen(navController)
                        }
                    }
                }
            }
        }
    }
}
