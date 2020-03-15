package com.uhfsolutions.roomdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.uhfsolutions.roomdatabase.model.Post
import com.uhfsolutions.roomdatabase.mvvm.Repository
import com.uhfsolutions.roomdatabase.mvvm.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val repo = Repository(this)

        val context = this

        val viewModel =
            ViewModelProviders.of(this@MainActivity, object : ViewModelProvider.Factory {
                override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
                    return ViewModel(context) as T
                }
            }).get(ViewModel::class.java)

        val observer = Observer<List<Post>> {
            Toast.makeText(context, "length: ${it!!.size}", Toast.LENGTH_LONG).show()
        }

            viewModel.showAllPosts()
                .observe(context, object : Observer<List<Post>> {
                    override fun onChanged(t: List<Post>?) {
                        Toast.makeText(
                            context,
                            "Length.. ${t!!.size}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })

//                val l = viewModel.getPosts()


        textView = findViewById(R.id.textView)

        val encrypt = findViewById<Button>(R.id.button)
        val decrypt = findViewById<Button>(R.id.button2)



        encrypt.setOnClickListener {
            launch {
                viewModel.getAllPost()
                viewModel.insertAllPost()

            }


        }
        decrypt.setOnClickListener {

        }
    }

    override val coroutineContext: CoroutineContext
        get() = IO
}
