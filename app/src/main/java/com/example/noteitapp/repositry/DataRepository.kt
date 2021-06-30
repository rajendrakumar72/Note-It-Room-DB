package com.example.noteitapp.repositry

import androidx.lifecycle.LiveData
import com.example.noteitapp.data.UserDatabase
import com.example.noteitapp.model.DataClass
import com.example.noteitapp.data.DataDao

class DataRepository (private val dataDao: DataDao){

    val readAllData:LiveData<List<DataClass>> =dataDao.readAllData()

    suspend fun addData(dataClass: DataClass){
        dataDao.addData(dataClass)
    }

    suspend fun updateData(dataClass:DataClass){
        dataDao.updateData(dataClass)
    }

    suspend fun deleteData(dataClass: DataClass){
        dataDao.deleteData(dataClass)
    }

    suspend fun deleteAllData(){
        dataDao.deleteAllData()
    }
}