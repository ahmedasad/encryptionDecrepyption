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

    private lateinit var textView: TextView
    private lateinit var encrypter: EnCryptor
    private lateinit var encryptionKeyStoreImpl: EncryptionKeyStoreImpl
    private lateinit var deCryptor: DeCryptor

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

        encrypt.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                for (item in list) {
                    encryptObj(item)
                }
                repo.insertAllClasses(list!!)
            }
        }

        decrypt.setOnClickListener {
            CoroutineScope(IO).launch {
                val list = repo.getAllStudent()
                for (item in list) {
                    decryptObj(item)
                }
            }
        }

    }


    fun encryptObj(classObject: Any): Any {
        val f = classObject.javaClass.declaredFields
        for (item in f) {
            val field = classObject.javaClass.getDeclaredField(item.name)
            field.isAccessible = true
            val value = field.get(classObject) //getting value if specific field
            if (value.javaClass.name != "int")
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
            if (value.javaClass.name != "int")
                field.set(classObject, encryptionKeyStoreImpl.decrypt(value?.toString()))
        }
        return classObject
    }

}
