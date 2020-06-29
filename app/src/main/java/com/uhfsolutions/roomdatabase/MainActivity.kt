package com.uhfsolutions.roomdatabase

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import com.uhfsolutions.roomdatabase.bundle.TransformBundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val car = Car()
        car.id = "4"
        car.model = "Honda"
        car.quantity = 5

        btn_go.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,MainActivity2::class.java)
            val bundle = TransformBundle.setBundle(car)
            intent.putExtras(bundle)
            startActivity(intent)
        })


    }

    override val coroutineContext: CoroutineContext
        get() = IO
}


class Car{
    var id:String? = null
    var model:String? = null
    var quantity:Int = 1
}