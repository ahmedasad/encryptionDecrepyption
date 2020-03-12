package com.uhfsolutions.roomdatabase.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ListOfClass {
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null
    var list:List<Clazz>? = null
}