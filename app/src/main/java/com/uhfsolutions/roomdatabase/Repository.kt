package com.uhfsolutions.roomdatabase

import android.content.Context
import com.uhfsolutions.roomdatabase.model.Clazz

class Repository(context:Context){
    private val dbRoom = DataBaseRoom(context)
    private val dao = dbRoom.dao()

    suspend fun insertAllClasses(list:List<Clazz>){
        dao.insertSchool(list)
    }

    suspend fun getAllStudent():List<Clazz>{
        return dao.getAllStudents()
    }
}