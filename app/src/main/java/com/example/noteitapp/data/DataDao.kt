package com.example.noteitapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.noteitapp.model.DataClass

@Dao
interface DataDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addData(dataClass: DataClass)

    @Update
    suspend fun updateData(dataClass: DataClass)

    @Delete
    suspend fun deleteData(dataClass: DataClass)

    @Query("DELETE FROM dataTable")
    suspend fun deleteAllData()

    @Query("SELECT * FROM dataTable ORDER BY id ASC")
    fun readAllData():LiveData<List<DataClass>>
}