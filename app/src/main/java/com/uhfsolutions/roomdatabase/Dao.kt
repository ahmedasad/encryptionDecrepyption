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

//    @Insert
//    fun insertStudent(school: School)
//
//    @Insert
//    fun insertClass(school: School)
//
//    @Delete
//    fun deleteSchool(school: School)

//    @Delete
//    fun deleteStudent(school: School)
//
//    @Delete
//    fun deleteClass(school: School)
//
    @Query("select * from clazz")
    suspend fun getAllStudents():List<Clazz>
//
//    @Query("select * from class")
//    fun getAllClass()
//
//    @Query("select studentId,studentName from student inner join class on  courseId = :studentId")
//    fun getStudentsFromCertainClass(studentId: Int)

}