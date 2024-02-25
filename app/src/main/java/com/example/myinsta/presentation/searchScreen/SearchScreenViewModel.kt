package com.example.myinsta.presentation.searchScreen


import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myinsta.common.Constants
import com.example.myinsta.models.User
import com.example.myinsta.response.Resource
import com.example.myinsta.useCases.FirebaseGetUserByName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel
@Inject constructor(
    private val firebaseGetUserByName: FirebaseGetUserByName
) : ViewModel() {

    private val _usersByName = MutableStateFlow<List<User>>(emptyList())
    val usersByName: StateFlow<List<User>> get() = _usersByName

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()
    val loading = mutableStateOf(false)


    fun getUsersByName(
        name:String
    ){
        viewModelScope.launch {
            loading.value = true
            firebaseGetUserByName.execute(name).collect{ response->
                when (response) {
                    is Resource.Error -> {
                        Log.d(Constants.TAG, "Error response")
                    }
                    is Resource.Loading -> {
                        loading.value = true
                        Log.d(Constants.TAG, "Loading")
                    }
                    is Resource.Success -> {
                        _usersByName.value = response.data ?: emptyList()
                        loading.value = false
                    }
                }
            }
        }
    }
    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
    }
}