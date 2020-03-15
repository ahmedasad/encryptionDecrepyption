package com.uhfsolutions.roomdatabase.mvvm

import android.content.Context
import com.uhfsolutions.roomdatabase.Encryption.EncryptionKeyStoreImpl
import com.uhfsolutions.roomdatabase.MyApplication
import com.uhfsolutions.roomdatabase.model.Post
import com.uhfsolutions.roomdatabase.retrofit.RetrofitClient
import com.uhfsolutions.roomdatabase.room.DataBaseRoom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import retrofit2.Response
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class Repository(context:Context):CoroutineScope{
    private val dbRoom = DataBaseRoom(context)
    private val dao = dbRoom.dao()
    private val client = RetrofitClient().jsonApi
    private val encryptionDecryptionAccess:EncryptionKeyStoreImpl
    init {
        MyApplication.init(context)
        encryptionDecryptionAccess = MyApplication.encryptionImplementation()
    }

    suspend fun insertAllPostInDB(){
        var list = ArrayList<Post>()
        val response = getAllPostFromWebService()

        try{
            if(response.code() == 200){
                list.addAll(response.body()!!)
                println("#DEBUG MSG  .. ${response.message()}")

                encryptionDecryptionAccess.encryptList(list)
                dao.insertPost(list)
            }
            else{

                println("#ERROR  .. ${response.errorBody().toString()}")
            }

        }
        catch (e:Exception){
            println("#ERROR.. ${e.message}")
        }
    }

    fun getAllPostFromDB():List<Post>{
         return dao.getAllPosts()
    }

    suspend fun getAllPostFromWebService(): Response<List<Post>> {
        return client.getPosts()
    }

    override val coroutineContext: CoroutineContext
        get() = IO
}