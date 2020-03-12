package com.uhfsolutions.roomdatabase.model

import androidx.room.Entity
import androidx.room.PrimaryKey


class Student {
    var studentId:Int = 0
    var courseId:Int = 0
    var studentName:String? = null
    var studentAddress:String? = null
    var studentPhoneNumber:String? = null
}