package com.uhfsolutions.roomdatabase.Encryption

import android.content.Context
import java.io.FileInputStream
import java.io.FileOutputStream
import java.security.KeyStore
import java.security.KeyStoreException
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey


class EncryptionKeyStoreImpl {


    var key: SecretKey? = null
    private var encryptor: Encryptor? = null

    private var decryptor: Decryptor? = null

    /**
     * Making this class Singleton
     * Using Bill Pugh Singleton Implementation
     */
    companion object {
        val SAMPLE_ALIAS = "MYALIAS22"
    }

    init {
        try {
            encryptor = Encryptor()
            decryptor = Decryptor()
            generateKey()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun encryptList(list: List<Any>): List<Any> {
        for (item in list) {
            encryptObject(item)
        }
        return list
    }

    fun decryptList(list: List<Any>): List<Any> {
        for (item in list) {
            decryptObject(item)
        }
        return list
    }


    fun encryptObject(classObject: Any): Any {
        val f = classObject.javaClass.declaredFields
        for (item in f) {
            val field = classObject.javaClass.getDeclaredField(item.name)
            field.isAccessible = true
            val value = field.get(classObject) //getting value if specific field
            if (value.javaClass.name != "java.lang.Integer")
                field.set(classObject, encrypt(value?.toString()))
        }
        return classObject
    }

    fun decryptObject(classObject: Any): Any {
        val f = classObject.javaClass.declaredFields
        for (item in f) {
            val field = classObject.javaClass.getDeclaredField(item.name)
            field.isAccessible = true
            val value = field.get(classObject) //getting value if specific field
            if (value.javaClass.name != "java.lang.Integer")
                field.set(classObject, decrypt(value?.toString()))
        }
        return classObject
    }

    private fun encrypt(value: String?): String? {
        return try {
            encryptor!!.encryptText(value!!, key)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    }

    private fun decrypt(value: String?): String? {
        return try {
            decryptor?.decrypt(value!!, key)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun loadKey() {
        try { // Load Keystore
            val fis =
                FileInputStream(context!!.filesDir.absolutePath.toString() + "/OEKeyStore")
            ks!!.load(fis, password)
            // Load the secret key
            val secretKeyEntry: KeyStore.SecretKeyEntry =
                ks!!.getEntry(EncryptionKeyStoreImpl.SAMPLE_ALIAS, null) as KeyStore.SecretKeyEntry
            key = secretKeyEntry.secretKey

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    //region For saving and retrieving keystore
    private var ks: KeyStore? = null
    private val password = "1234567890".toCharArray()
    private var context: Context? = null
    fun setContext(context: Context) {
        this.context = context
        try {
            ks = KeyStore.getInstance(KeyStore.getDefaultType())
        } catch (e: KeyStoreException) {
            e.printStackTrace()
        }
    }

    private fun generateKey() {
        try {
            ks?.load(null, password)
            val kg: KeyGenerator = KeyGenerator.getInstance("AES")
            kg.init(128)
            val key: SecretKey = kg.generateKey()
            val secretKeyEntry: KeyStore.SecretKeyEntry = KeyStore.SecretKeyEntry(key)
            ks!!.setEntry(EncryptionKeyStoreImpl.SAMPLE_ALIAS, secretKeyEntry, null)
            val fos =
                FileOutputStream(context!!.filesDir.absolutePath.toString() + "/OEKeyStore")
            ks!!.store(fos, password)
            loadKey()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}