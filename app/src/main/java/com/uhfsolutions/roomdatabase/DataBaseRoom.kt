package com.uhfsolutions.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uhfsolutions.roomdatabase.model.Clazz

@Database(entities = [Clazz::class], version = 2)
abstract class DataBaseRoom: RoomDatabase() {
    abstract fun dao(): Dao

    companion object {
        private var dbInstance: DataBaseRoom? = null
        private var LOOK = Any()
        operator fun invoke(context: Context) = dbInstance ?: synchronized(LOOK) {
            dbInstance ?: buildDbInstance(context).also {
                dbInstance = it
            }
        }

        fun buildDbInstance(context: Context) =
            Room.databaseBuilder(context, DataBaseRoom::class.java, "class.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}