/*
package com.example.baby.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baby.data.TodoEntity
import com.example.baby.network.TodoRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class TodoViewModel(private val todoDao: TodoDao) : ViewModel() {

    // 외부에서 주입해주는 게 낫대 인터페이스로 빼주기
    private val repository: TodoRepository = TodoRepository(todoDao)

//    private val _todos = MutableStateFlow<List<TodoEntity>>(emptyList())
//    val todos: StateFlow<List<TodoEntity>> get() = _todos

    val todos2 = channelFlow{
        repository.getAllTodos().collect{
            trySend(it)
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val djdj = repository.getAllTodos().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

//    init {
//        loadTodos()
//    }
//
//    private fun loadTodos() {
//        viewModelScope.launch {
//            repository.getAllTodos().collect { todoList ->
//                _todos.value = todoList
//            }
//        }
//    }


    fun insert(todo: TodoEntity) = viewModelScope.launch {
        repository.insert(todo)
    }

    fun update(todo: TodoEntity) = viewModelScope.launch {
        repository.update(todo)
    }

    fun delete(todo: TodoEntity) = viewModelScope.launch {
        repository.delete(todo)
    }
}
*/
