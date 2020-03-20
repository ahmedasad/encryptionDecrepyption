package com.uhfsolutions.roomdatabase.mvvm

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uhfsolutions.roomdatabase.model.Post
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class ViewModelPost(context: Context) : ViewModel() {

    private val repository = Repository(context)

    private val allPosts: MutableLiveData<List<Post>> = MutableLiveData()

    fun showAllPosts(): LiveData<List<Post>> {
        return allPosts
    }

    suspend fun getAllPost() {
        withContext(IO) {
            val list = async { repository.getAllPostFromDB() }.await()
            withContext(Main){allPosts.value = list}
        }
    }

    suspend fun insertAllPost() {
        withContext(IO) {
            repository.insertAllPostInDB()
        }
    }
}
