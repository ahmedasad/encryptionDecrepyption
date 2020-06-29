package com.uhfsolutions.roomdatabase.bundle

import android.os.Bundle

object TransformBundle {
    fun getData(bundle: Bundle,classObject: Any):Any{
        val f = classObject.javaClass.declaredFields
        for (item in f) {
            val field = classObject.javaClass.getDeclaredField(item.name) // field/member of class
            field.isAccessible = true
            val value = field.get(classObject) //getting value of specific field
            if(field.type.name.equals("int")){
                field.setInt(classObject,bundle.getInt(field.name).toString().toInt())
            }
            else{
                field.set(classObject,bundle.getString(field.name))
            }
        }
        return classObject
    }

    fun setBundle(classObject: Any):Bundle{
        val bundle = Bundle()
        val f = classObject.javaClass.declaredFields
        for (item in f) {
            val field = classObject.javaClass.getDeclaredField(item.name) // field/member of class
            field.isAccessible = true
            val value = field.get(classObject) //getting value of specific field
            if (value.javaClass.name == "java.lang.Integer") bundle.putInt(field.name,value?.toString()!!.toInt())
            else bundle.putString(field.name,value?.toString())
        }
        return bundle
    }

}
