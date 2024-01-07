package com.example.baby

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.baby.dao.TodoDao
import com.example.baby.network.TodoDatabase
import com.example.baby.screen.MainScreen
import com.example.baby.screen.WriteScreen
import com.example.baby.ui.theme.BabyTheme
import com.example.baby.viewModel.DateViewModel
import com.example.baby.viewModel.DateViewModelFactory
import com.example.baby.viewModel.TodoViewModel
import com.example.baby.viewModel.TodoViewModelFactory
import java.util.Date

class MainActivity : ComponentActivity() {

    private lateinit var todoDao: TodoDao

    val todoViewModel = ViewModelProvider(
        this,
        TodoViewModelFactory(todoDao)
    ).get(TodoViewModel::class.java)

    val dateViewModel by viewModels<DateViewModel>()

//    val dateViewModel = ViewModelProvider(
//        this,
//        DateViewModelFactory()
//    ).get(DateViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        todoDao = TodoDatabase.getDatabase(applicationContext).todoDao()

        setContent {
            BabyTheme {

                val navController = rememberNavController()

                // NavHost 설정
                NavHost(navController = navController, startDestination = "mainScreen") {
                    composable("mainScreen") {
                        MainScreen(todoViewModel = todoViewModel, dateViewModel = dateViewModel, navController = navController)
                    }
                    composable("secondScreen") {
                        WriteScreen(todoViewModel = todoViewModel, navController = navController)
                    }
                }
            }
        }
    }
}