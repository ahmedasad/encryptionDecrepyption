package com.uhfsolutions.roomdatabase.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity
class ListOfPosts {
//    @PrimaryKey(autoGenerate = true)
    var id:Int? = null
    var list: List<Post>? = null
}