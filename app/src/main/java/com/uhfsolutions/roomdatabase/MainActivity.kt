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
        encryptionKeyStoreImpl.loadKey()
        deCryptor = DeCryptor()

        textView = findViewById(R.id.textView)
        val repo = Repository(this)

        val encrypt = findViewById<Button>(R.id.button)
        val decrypt = findViewById<Button>(R.id.button2)

        val cl = Clazz()


        cl.id = 1
        cl.courseId = "121"
        cl.courseName = "Maths"

        val cls = Clazz()

        cls.id = 2
        cls.courseId = "10"
        cls.courseName = "English"

        val list = ArrayList<Clazz>()

        list.add(cl)
        list.add(cls)

        encrypt.setOnClickListener {
            CoroutineScope(IO).launch {

                    encryptionKeyStoreImpl.encryptList(list)
                for (item in list) {
                    println("#result id .. ${item.id}")
                    println("#result CId .. ${item.courseId}")
                    println("#result CName .. ${item.courseName}")
            }
//                repo.insertAllClasses(list!!)
            }
        }

        decrypt.setOnClickListener {
            CoroutineScope(IO).launch {

                    encryptionKeyStoreImpl.decryptList(list)
                for (item in list) {
                    println("#result id .. ${item.id}")
                    println("#result CId .. ${item.courseId}")
                    println("#result CName .. ${item.courseName}")
                }
            }
        }
    }
}
