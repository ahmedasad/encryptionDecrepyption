package com.uhfsolutions.roomdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.uhfsolutions.roomdatabase.Paging.model.StackExchResp
import com.uhfsolutions.roomdatabase.Paging.retrofit.WebApiClient
import com.uhfsolutions.roomdatabase.model.Post
import com.uhfsolutions.roomdatabase.mvvm.Repository
import com.uhfsolutions.roomdatabase.mvvm.ViewModelPost
import com.uhfsolutions.roomdatabase.retrofit.RetrofitClient
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val context = this

        val viewModel =
            ViewModelProviders.of(this@MainActivity, object : ViewModelProvider.Factory {
                override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
                    return ViewModelPost(context) as T
                }
            }).get(ViewModelPost::class.java)

        val observer = Observer<List<Post>> {
            Toast.makeText(context, "length: ${it!!.size}", Toast.LENGTH_LONG).show()
        }

            viewModel.showAllPosts()
                .observe(context, observer)

//                val l = viewModel.getPosts()


        textView = findViewById(R.id.textView)

        val encrypt = findViewById<Button>(R.id.button)
        val decrypt = findViewById<Button>(R.id.button2)



        encrypt.setOnClickListener {
//            launch {
//                viewModel.getAllPost()
//                viewModel.insertAllPost()
//
//            }

            val call = WebApiClient.client.getData(1,50,"stackoverflow")
            call.enqueue(object: Callback<StackExchResp>{
                override fun onFailure(call: Call<StackExchResp>, t: Throwable) {
                    Toast.makeText(context,"${t.localizedMessage}",Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<StackExchResp>, response: Response<StackExchResp>) {
                    val response = response.body()
                }
            })
        }
        decrypt.setOnClickListener {

        }
    }

    override val coroutineContext: CoroutineContext
        get() = IO
}
