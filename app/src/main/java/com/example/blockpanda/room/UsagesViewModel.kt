package com.example.blockpanda.room

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class UsagesViewModel(private val usagesRepository: UsagesRepository) : ViewModel() {

    val getAllUserList: LiveData<MutableList<Usages>> = usagesRepository.getAllAppUsages.asLiveData()

    fun getAppUsages(pkgName: String): Usages {
        return usagesRepository.getAppUsages(pkgName)
    }

    // Launching a new coroutine to insert the data in a non-blocking way
    fun insertAppUsages(user: Usages) = viewModelScope.launch {
        usagesRepository.insertAppUsages(user)
    }

    fun updateAppUsages(user: Usages) = viewModelScope.launch {
        usagesRepository.updateAppUsages(user)
    }

    fun deleteAppUsages(user: Usages) = viewModelScope.launch {
        usagesRepository.deleteAppUsages(user)
    }

    class UsagesViewModelFactory(private val usagesRepository: UsagesRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UsagesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UsagesViewModel(usagesRepository) as T
            }
            throw IllegalArgumentException("Unknown VieModel Class")
        }

    }
}
