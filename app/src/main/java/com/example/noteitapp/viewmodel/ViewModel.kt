package com.example.noteitapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.noteitapp.data.UserDatabase
import com.example.noteitapp.model.DataClass
import com.example.noteitapp.repositry.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel(application: Application):AndroidViewModel(application) {

    val readAllData:LiveData<List<DataClass>>
    private val repository:DataRepository

    init {
        val dataDao=UserDatabase.getDatabase(application).dataDao()
        repository= DataRepository(dataDao)
        readAllData=repository.readAllData
    }

    fun addData(dataClass: DataClass){
        viewModelScope.launch (Dispatchers.IO){
            repository.addData(dataClass)
        }
    }

    fun updateData(dataClass: DataClass){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(dataClass)
        }
    }

    fun deleteData(dataClass: DataClass){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(dataClass)
        }
    }

    fun deleteAllData(){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteAllData()
        }
    }
}