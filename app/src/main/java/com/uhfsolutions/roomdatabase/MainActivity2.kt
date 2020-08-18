package com.uhfsolutions.roomdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uhfsolutions.roomdatabase.bundle.TransformBundle
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        button.setOnClickListener {
            val d = intent.extras

        }
    }
}