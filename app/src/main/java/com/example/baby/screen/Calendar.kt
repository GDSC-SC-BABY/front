package com.example.baby.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.baby.data.TodoEntity
import com.example.baby.ui.theme.mainColor
import com.example.baby.ui.theme.todoListColor
import com.example.baby.viewModel.TodoViewModel


@Composable
fun Calendar(today: String) {
    Text(text = today, fontWeight = FontWeight.SemiBold, color = Color(0xff878787))
}