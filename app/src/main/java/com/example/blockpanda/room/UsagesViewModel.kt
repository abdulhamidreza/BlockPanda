package com.example.blockpanda.room

import android.app.usage.UsageStats
import android.content.pm.PackageManager
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UsagesViewModel(private val usagesRepository: UsagesRepository) : ViewModel() {

    private val TAG = "UsagesViewModel***********"

    val getAllUserList: LiveData<MutableList<Usages>> = usagesRepository.getAllAppUsages.asLiveData()

    fun getAllAppUsagesOnce(): Deferred<List<Usages>> {
        return viewModelScope.async(Dispatchers.IO) { usagesRepository.getAllAppUsagesOnce() }
    }

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

    fun checkForAppStatus(user: Usages) = viewModelScope.launch {
        usagesRepository.deleteAppUsages(user)
    }


    fun insertNewApp(appPkg: ArrayList<UsageStats>, mPm: PackageManager?) {

        viewModelScope.launch(Dispatchers.Default) {
            val appDb = getAllAppUsagesOnce().await()
            //Add
            for (i in appPkg) {
                var found = false
                for (j in appDb) {
                    if (i.packageName.equals(j.pkgName))
                        found = true
                }
                if (!found) {
                    val appInfo = mPm!!.getApplicationInfo(i.packageName, 0)
                    val label = appInfo.loadLabel(mPm!!).toString()
                    Log.e(TAG, "insertAppUsages() called " + label)
                    insertAppUsages(
                        Usages(
                            i.packageName, label, false, false,
                            false, false, false, "",
                            "", "", "", false, "",
                            "", false
                        )
                    )
                }
            }
            //Remove
            for (i in appDb) {
                var found = false
                for (j in appPkg) {
                    if (i.pkgName.equals(j.packageName))
                        found = true
                }
                if (!found) {
                    Log.e(TAG, "deleteAppUsages() called " + i.appName)
                    deleteAppUsages(i)
                }
            }

        }

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
