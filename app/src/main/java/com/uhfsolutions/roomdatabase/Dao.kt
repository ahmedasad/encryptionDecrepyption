package com.uhfsolutions.roomdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uhfsolutions.roomdatabase.model.Clazz

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchool(list:List<Clazz>)

    @Query("select * from clazz")
    suspend fun getAllStudents():List<Clazz>


}