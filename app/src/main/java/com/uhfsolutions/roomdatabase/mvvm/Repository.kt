package com.uhfsolutions.roomdatabase.mvvm

import android.content.Context
import com.uhfsolutions.roomdatabase.Encryption.EncryptionKeyStoreImpl
import com.uhfsolutions.roomdatabase.MyApplication
import com.uhfsolutions.roomdatabase.model.Post
import com.uhfsolutions.roomdatabase.retrofit.RetrofitClient
import com.uhfsolutions.roomdatabase.room.DataBaseRoom
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import retrofit2.Response
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

object Repository:CoroutineScope{

    private lateinit var context:Context

    fun setContext(cont:Context){
        context = cont
    }
    fun getContext():Context {
        return context
    }
    private val dbRoom = DataBaseRoom(getContext())
    private val dao = dbRoom.dao()
    private val client = RetrofitClient.client
    private val encryptionDecryptionAccess:EncryptionKeyStoreImpl
    init {
        MyApplication.init(getContext())
        encryptionDecryptionAccess = MyApplication.encryptionImplementation()
    }

    suspend fun insertAllPostInDB(){
        var list = ArrayList<Post>()

//        withContext(IO){
//            val job = withTimeoutOrNull(1000) {
//                response = getAllPostFromWebService()
//                try{
//                    if(response.code() == 200){
//                        list.addAll(response.body()!!)
//                        println("#DEBUG MSG  .. ${response.message()}")
//
//                        encryptionDecryptionAccess.encryptList(list)
//                        dao.insertPost(list)
//                    }
//                    else{
//
//                        println("#ERROR  .. ${response.errorBody().toString()}")
//                    }
//
//                }
//                catch (e:Exception){
//                    println("#ERROR.. ${e.message}")
//                }
//            }
//
//            if(job == null){
//                println("#ERROR... timeout" )
//            }
//        }
        val response = getAllPostFromWebService()

        try{
            if(response.code() == 200){
                list.addAll(response.body()!!)
                println("#DEBUG MSG  .. ${response.code()}")

                encryptionDecryptionAccess.encryptList(list)
                dao.insertPost(list)
            }
            else{

                println("#ERROR  .. ${response.errorBody().toString()}")
            }

        }
        catch (e:CancellationException){
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