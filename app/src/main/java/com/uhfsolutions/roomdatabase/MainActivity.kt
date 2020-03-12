package com.uhfsolutions.roomdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.uhfsolutions.roomdatabase.Encryption.DeCryptor
import com.uhfsolutions.roomdatabase.Encryption.EnCryptor
import com.uhfsolutions.roomdatabase.Encryption.EncryptionKeyStoreImpl
import com.uhfsolutions.roomdatabase.model.Clazz
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlin.reflect.typeOf

class MainActivity : AppCompatActivity() {

    lateinit var textView: TextView
    lateinit var encrypter: EnCryptor
    lateinit var encryptionKeyStoreImpl: EncryptionKeyStoreImpl
    lateinit var deCryptor: DeCryptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        encrypter = EnCryptor()
        encryptionKeyStoreImpl = EncryptionKeyStoreImpl()
        encryptionKeyStoreImpl.setContext(this)
        encryptionKeyStoreImpl.GenerateKey()
        encryptionKeyStoreImpl.loadKey()
        deCryptor = DeCryptor()

        textView = findViewById(R.id.textView)
        val repo = Repository(this)

        val encrypt = findViewById<Button>(R.id.button)
        val decrypt = findViewById<Button>(R.id.button2)
        val cl = Clazz()
        cl.courseId = "10"
        cl.courseName = "Maths"

        val cls = Clazz()
        cls.courseId = "10"
        cls.courseName = "Maths"

        val list = ArrayList<Clazz>()

        list.add(cl)
        list.add(cls)
//        println("#result length.. "+list.size)

//        println("#result Before.. "+cl.courseId)
//        println("#result Before.. "+cl.courseName)
        encrypt.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                //                requestApi()
                for (item in list) {

//            println("#result Before Id..  ${item.courseId}")
//            println("#result Before CName..  ${item.courseName}")
                    encryptObj(item)
//
//            println("#result Encrypt Id..  ${item.id}")
//
//            println("#result Encrypt CId..  ${item.courseId}")
//            println("#result Encrypt CName..  ${item.courseName}")
                }
                repo.insertAllClasses(list!!)
            }
        }

        decrypt.setOnClickListener {
            CoroutineScope(IO).launch {
                val list = repo.getAllStudent()
                for (item in list) {
                    println("#result Before Decrypt..  ${item.courseId}")
                    println("#result Before Decrypt..  ${item.courseName}")
                    decryptObj(item)

                    println("#result After..  ${item.courseId}")
                    println("#result After..  ${item.courseName}")
                }
            }
        }

    }

//    suspend fun requestApi(){
//        val result =  getResultFrom1ApI()
//        println("#debug  result..$result")
//        withContext(Dispatchers.Main){
//            textView.text = "Done"
//        }
//    }
//
//    suspend fun getResultFrom1ApI():String {
//        logThread("getresultFrom1ApI")
//        delay(1000)
//        return "Result #1"
//    }
//
//    private fun logThread(name:String) {
//        println("#debug  Thread Name: "+Thread.currentThread().name)
//    }

    fun encryptObj(classObject: Any): Any {

        val f = classObject.javaClass.declaredFields
        for (item in f) {
            val field = classObject.javaClass.getDeclaredField(item.name)
            field.isAccessible = true
            val value = field.get(classObject) //getting value if specific field

            field.set(classObject, encryptionKeyStoreImpl.encrypt(value?.toString()))
        }
        return classObject
    }

    fun decryptObj(classObject: Any): Any {
        val f = classObject.javaClass.declaredFields
        for (item in f) {
            val field = classObject.javaClass.getDeclaredField(item.name)

            field.isAccessible = true
            val value = field.get(classObject) //getting value if specific field
            field.set(classObject, encryptionKeyStoreImpl.decrypt(value?.toString()))
        }
        return classObject
    }

}
