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
    private var encryptor: EnCryptor? = null

    private var decryptor: DeCryptor? = null

    /**
     * Making this class Singleton
     * Using Bill Pugh Singleton Implementation
     */
    companion object SingletonHelper {
        private val _Instance = EncryptionKeyStoreImpl()
        val SAMPLE_ALIAS = "MYALIAS22"
        private val TAG = "encript"
    }

    init {
        encryptor = EnCryptor()
        try {
            decryptor = DeCryptor()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun encrypt(value: String?): String? {
        try {
            return encryptor!!.encryptText(value!!, key)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun decrypt(value: String?): String? { //        String decriptedText = "";
        return decryptor?.decrypt(value!!, key)
    }
    fun loadKey(){
        try { // Load Keystore
            val fis =
                FileInputStream(context!!.getFilesDir().getAbsolutePath().toString() + "/OEKeyStore")
            ks!!.load(fis, password)
            // Load the secret key
            val secretKeyEntry: KeyStore.SecretKeyEntry =
                ks!!.getEntry(EncryptionKeyStoreImpl.SAMPLE_ALIAS, null) as KeyStore.SecretKeyEntry
            key = secretKeyEntry.getSecretKey()

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

    fun GenerateKey() {
        try {
            ks?.load(null, password)
            val kg: KeyGenerator = KeyGenerator.getInstance("AES")
            kg.init(128)
            val key: SecretKey = kg.generateKey()
            val secretKeyEntry: KeyStore.SecretKeyEntry = KeyStore.SecretKeyEntry(key)
            ks!!.setEntry(EncryptionKeyStoreImpl.SAMPLE_ALIAS, secretKeyEntry, null)
            val fos =
                FileOutputStream(context!!.getFilesDir().getAbsolutePath().toString() + "/OEKeyStore")
            ks!!.store(fos, password)
            loadKey()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun SaveKey() {
        try {
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }


}