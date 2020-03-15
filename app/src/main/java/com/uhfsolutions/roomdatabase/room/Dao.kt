package com.uhfsolutions.roomdatabase.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uhfsolutions.roomdatabase.model.Post


@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPost(list:List<Post>)

    @Query("select * from post")
    fun getAllPosts():List<Post>


}