package com.uhfsolutions.roomdatabase

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


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
}

sealed class Result(){
    data class Response(val response:Any): Result()
    data class Error(val error:String): Result()
}

interface Response{
    fun response(response:Any)
    fun failure(failure:String)
}
