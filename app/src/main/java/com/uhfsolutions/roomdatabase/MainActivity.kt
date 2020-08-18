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

        val adapter = AdapterViewPager(supportFragmentManager)
        val f1 = Fragment()
        val f2 = Fragment()
        val f3 = Fragment()
        adapter.addFragment(f1, "")
        adapter.addFragment(f2, "")
        adapter.addFragment(f3, "")
        findViewById<ViewPager>(R.id.pager).setAdapter(adapter)
        findViewById<TabLayout>(R.id.tabLayout).setupWithViewPager(findViewById<ViewPager>(R.id.pager))
        val slidingTabStrip = findViewById<TabLayout>(R.id.tabLayout).getChildAt(0) as ViewGroup
        for(i in 0 until (slidingTabStrip.children.count())){
            val v = slidingTabStrip.getChildAt(i)
            val params =  v.getLayoutParams() as (ViewGroup.MarginLayoutParams)
            params.rightMargin = 8;
        }


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