package com.hrishi.spendora.screens


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrishi.spendora.R
import com.hrishi.spendora.repository.ExpenseEvent
import com.hrishi.spendora.repository.ExpenseState
import com.hrishi.spendora.repository.SortType
import com.hrishi.spendora.ui.theme.green
import com.hrishi.spendora.ui.theme.greenDark
import com.hrishi.spendora.ui.theme.greenLighter

var counter = 0
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseListScreen(
    //navController: NavHostController,
    state: ExpenseState,
    onEvent: (ExpenseEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(ExpenseEvent.ShowDialog)
                },
                containerColor = greenLighter
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .background(greenDark)
                .height(80.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 13.dp),
                text = "Expenses",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = greenLighter
            )
            Spacer(modifier = Modifier.weight(1f))

            val context = LocalContext.current
            var expanded by remember { mutableStateOf(false) }

            Box(

            ) {
                IconButton(
                    modifier = Modifier
                        .padding(end = 13.dp)
                        .size(25.dp),
                    onClick = {
                        expanded = !expanded
                    }
                ) {
                    Text(text = "Sort")
                    Image(
                        painter = painterResource(id = R.drawable.sort),
                        contentDescription = "sort"
                    )
                }
                DropdownMenu(
                    modifier = Modifier
                        .padding(5.dp),
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    Text(text = "Sorting options")
                    DropdownMenuItem(
                        text = { Text("Expense") },
                        onClick = {
                            if (counter % 2 == 0) {
                                onEvent(ExpenseEvent.SortExpenses(SortType.ASCENDING))
                            }
                            else {
                                onEvent(ExpenseEvent.SortExpenses(SortType.DESCENDING))
                            }
                            counter += 1
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Date") },
                        onClick = {
                            Toast.makeText(
                                context,
                                "Feature not implemented, sorry :(",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    )
                }
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(top = 20.dp, start = 10.dp, end = 10.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp)
                .background(green),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            items(state.expenses) { expense ->
                Row(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(15.dp))
                        .background(greenLighter)
                        .padding(10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(6.dp)
                    ) {
                        Text(
                            text = expense.expenseName,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 24.sp,
                            fontFamily = FontFamily.SansSerif,
                            //color = Color.
                        )
                        Text(
                            text = expense.expenseType,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            modifier = Modifier.padding(top = 10.dp),
                            text = stringResource(id = R.string.Rs) + expense.expenseAmount.toString(),
                            fontFamily = FontFamily.SansSerif
                        )
                    }
                    IconButton(
                        modifier = Modifier.padding(top = 15.dp, bottom = 15.dp),
                        onClick = {
                        onEvent(ExpenseEvent.DeleteExpense(expense))
                        }
                    ) {
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
