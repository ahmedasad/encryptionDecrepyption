package com.uhfsolutions.roomdatabase.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Clazz {
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null
    var courseId:String? = null
    var courseName:String? = null
}