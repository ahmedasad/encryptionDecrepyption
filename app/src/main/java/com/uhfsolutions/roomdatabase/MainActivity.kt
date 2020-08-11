package com.uhfsolutions.roomdatabase

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.uhfsolutions.roomdatabase.bundle.TransformBundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
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

    private fun isEmpty(view: ViewGroup):EmptyResult{
        var result = EmptyResult(false,"")
        for(item in view.children){
            if(item is ViewGroup)
                isEmpty(item)
            else if(item is EditText){
                if(isEmptyOrNull(item.text.toString())){
                    // setting values
                    result.empty = true
                    result.name = if(item.tag!=null) item.tag.toString() else "Check if any field left empty"
                    return result
                }
            }
        }
        return result
    }

    fun isEmptyOrNull(text: String):Boolean{
        return text == null || text.equals("")
    }


    override val coroutineContext: CoroutineContext
        get() = IO
}


class Car{
    var id:String? = null
    var model:String? = null
    var quantity:Int = 1
}

internal class EmptyResult(var empty: Boolean, var name: String)