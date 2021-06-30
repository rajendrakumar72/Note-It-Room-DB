package com.example.noteitapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable as Parcelable

@Parcelize
@Entity(tableName = "dataTable")
data class DataClass(
    @PrimaryKey(autoGenerate = true)
    val id:Int,

    val title:String,

    val description:String

): Parcelable
