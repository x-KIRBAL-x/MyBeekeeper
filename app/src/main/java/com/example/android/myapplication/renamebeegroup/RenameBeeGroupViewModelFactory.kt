package com.example.android.myapplication.renamebeegroup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.myapplication.database.BeeDatabaseDao

class RenameBeeGroupViewModelFactory(
    private val dataSource: BeeDatabaseDao,
    private val groupKey: Long) : ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RenameBeeGroupViewModel::class.java)){
            return RenameBeeGroupViewModel(dataSource, groupKey) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}