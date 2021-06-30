package com.example.noteitapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.noteitapp.model.DataClass


@Database(entities = [DataClass::class],version = 1,exportSchema = false)
abstract class UserDatabase: RoomDatabase() {

    abstract fun dataDao(): DataDao

    companion object{

        @Volatile
        private var INSTANCE:UserDatabase?=null

        fun getDatabase(context: Context):UserDatabase{
            val tempInstance= INSTANCE
            if (tempInstance!=null){
                return tempInstance
            }
           synchronized(this){
                val  instance=Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "userDatabase"
                ).build()
                INSTANCE=instance
                return instance
            }

        }
    }
}