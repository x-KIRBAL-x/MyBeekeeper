package com.example.android.myapplication.beehives

import androidx.constraintlayout.widget.Group
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.myapplication.database.BeeDatabaseDao
import com.example.android.myapplication.database.BeeGroup
import com.example.android.myapplication.database.Beehive
import kotlinx.coroutines.launch

class BeehivesViewModel(private val beeGroupKey: Long = 0L,
               dataSource: BeeDatabaseDao): ViewModel(){

    private val _navigateToBeeGroupsFragment = MutableLiveData<Boolean?>()
    private val _navigateToAddNewBeehiveFragment = MutableLiveData<Beehive?>()
    private val _navigateToGroupDeleteFragment = MutableLiveData<Boolean?>()
    private val _navigateToBeeDetailFragment = MutableLiveData<Long>()
    private val _navigateToRenameGroupFragment = MutableLiveData<Boolean?>()

    val database = dataSource

    val beehives = database.getAllBeehiveWithId(beeGroupKey)

    private val beegroup: LiveData<BeeGroup>

    init {
        beegroup = database.getGroupWithId(beeGroupKey)
    }


    fun getBeeGroup() = beegroup

    val navigateToBeeGroupsFragment: LiveData<Boolean?>
        get() = _navigateToBeeGroupsFragment

    val navigateToAddNewBeehiveFragment: LiveData<Beehive?>
        get() = _navigateToAddNewBeehiveFragment

    val navigateToGroupDeleteFragment: LiveData<Boolean?>
        get() = _navigateToGroupDeleteFragment

    val navigateToBeeDetailFragment
        get() = _navigateToBeeDetailFragment

    val navigateToRenameGroupFragment: LiveData<Boolean?>
        get() = _navigateToRenameGroupFragment


    fun doneNavigateToBeeGroupsFragment(){
        _navigateToBeeGroupsFragment.value = null
    }

    fun doneNavigateToAddNewBeehiveFragment(){
        _navigateToAddNewBeehiveFragment.value = null
    }

    fun doneNavigateToGroupDeleteFragment(){
        _navigateToGroupDeleteFragment.value = null
    }

    fun doneNavigateToBeeDetailFragment(){
        _navigateToBeeDetailFragment.value=null
    }

    fun  onBeehiveClicked(id: Long){
        _navigateToBeeDetailFragment.value=id
    }

    fun navigateToGroupDeleteFragment(){
        _navigateToGroupDeleteFragment.value = true
    }

    fun navigateToRenameGroupFragment(){
        _navigateToRenameGroupFragment.value=true
    }

    fun doneNavigateToRenameGroupFragment(){
        _navigateToRenameGroupFragment.value=null
    }

    fun clickOnClose(){
        _navigateToBeeGroupsFragment.value=true
    }


    private suspend fun insertBeehive(beehive: Beehive){
        database.insertHive(beehive)
    }

    fun clickOnAddNewBeehive(){
        viewModelScope.launch {
            val newBeehive = Beehive()
            insertBeehive(newBeehive)
            var currentBeehive = database.getLastBeehive() ?: return@launch
            _navigateToAddNewBeehiveFragment.value= currentBeehive
        }
    }

}