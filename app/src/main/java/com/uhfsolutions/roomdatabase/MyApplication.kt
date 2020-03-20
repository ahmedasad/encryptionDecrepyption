package com.uhfsolutions.roomdatabase

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.uhfsolutions.roomdatabase.Encryption.EncryptionKeyStoreImpl
import com.uhfsolutions.roomdatabase.Encryption.Encryptor


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        val context = this
        init(context)
    }
    companion object{
        private val encryptionKeyStoreImpl = EncryptionKeyStoreImpl()
        fun init(context: Context){
            encryptionKeyStoreImpl.setContext(context)
            encryptionKeyStoreImpl.generateKey()
            encryptionKeyStoreImpl.loadKey()
        }

        fun encryptionImplementation():EncryptionKeyStoreImpl{
            return encryptionKeyStoreImpl
        }

    }

}