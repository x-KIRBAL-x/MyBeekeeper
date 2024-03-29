package com.example.android.myapplication.addnewbeehive

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.myapplication.database.BeeDatabaseDao
import com.example.android.myapplication.database.Beehive
import kotlinx.coroutines.launch

class AddNewBeehiveViewModel(
    private val beeGroupKey: Long,
    private val beehiveKey: Long,
    dataSource: BeeDatabaseDao) : ViewModel() {


    val database = dataSource

    private val beehive: LiveData<Beehive>

    init {
        beehive = database.getBeehiveWithId(beehiveKey)
    }

    fun getBeehive() = beehive

    private val _clickDoneButton = MutableLiveData<Boolean?>()

    val clickDoneButton: LiveData<Boolean?>
        get() = _clickDoneButton

    fun clickDoneButton(){
        _clickDoneButton.value = true
    }

    fun donenavigating(){
        _clickDoneButton.value = null
    }

    fun setValue(name: String, queenyear: Int){
        viewModelScope.launch {
            var newBeeHive = database.getBeeHive(beehiveKey)
            newBeeHive.beehiveName = name
            newBeeHive.queenBeeYear = queenyear
            newBeeHive.groupId = beeGroupKey
            database.updateHive(newBeeHive)
        }
    }
}