package com.uhfsolutions.roomdatabase.model

import androidx.room.Entity
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class Post {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    var postId:Int = 0
    var userId:String = "0"
    var title:String = "null"
    var body:String = "null"
}