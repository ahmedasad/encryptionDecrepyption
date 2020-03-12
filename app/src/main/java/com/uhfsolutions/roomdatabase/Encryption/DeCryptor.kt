package com.uhfsolutions.roomdatabase.Encryption

import android.os.Build
import android.util.Base64
import java.io.IOException
import java.security.*
import javax.crypto.*
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.IvParameterSpec
import javax.security.cert.CertificateException


class DeCryptor internal constructor() {
    private var keyStore: KeyStore? = null
    @Throws(
        KeyStoreException::class,
        CertificateException::class,
        NoSuchAlgorithmException::class,
        IOException::class
    )
    private fun initKeyStore() {
        keyStore = KeyStore.getInstance(ANDROID_KEY_STORE)
    }


    init {
        initKeyStore()
    }
    @Throws(
        UnrecoverableEntryException::class,
        NoSuchAlgorithmException::class,
        KeyStoreException::class,
        NoSuchPaddingException::class,
        InvalidKeyException::class,
        BadPaddingException::class,
        IllegalBlockSizeException::class,
        InvalidAlgorithmParameterException::class
    )
    fun decryptData(
        alias: String,
        encryptedData: ByteArray?,
        encryptionIv: ByteArray?
    ): String {
        val cipher: Cipher = Cipher.getInstance(TRANSFORMATION)
        val spec = GCMParameterSpec(128, encryptionIv)
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(alias), spec)
        return String(cipher.doFinal(encryptedData))
    }

    @Throws(Exception::class)
    private fun generateIVBytes(cipher: Cipher): ByteArray {
        val ivBytes = ByteArray(12)
        random.nextBytes(ivBytes)
        return ivBytes
    }

    fun decrypt(encrypted: String, key: SecretKey?): String? {
        val encodedString: String
        try {
            val split =
                encrypted.split(IV_SEPARATOR).toTypedArray()
            require(split.size == 2) { "Passed data is incorrect. There was no IV specified with it." }
            val ivString = split[0]
            encodedString = split[1]
            var ivParameterSpec: IvParameterSpec
            val spec: GCMParameterSpec
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                spec = GCMParameterSpec(128, Base64.decode(ivString,Base64.DEFAULT))
            } else {
                spec = GCMParameterSpec(
                    128,
                    Base64.decode(ivString, Base64.DEFAULT)
                )
            }
            val cipher: Cipher = Cipher.getInstance(TRANSFORMATION)
            val ivBytes = generateIVBytes(cipher)
            cipher.init(Cipher.DECRYPT_MODE, key, spec)
            //            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(alias), ivParameterSpec);
            val encryptedData: ByteArray
            encryptedData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Base64.decode(encodedString,Base64.DEFAULT)
            } else {
                Base64.decode(encodedString, Base64.DEFAULT)
            }
            val decodedData: ByteArray = cipher.doFinal(encryptedData)
            return String(decodedData)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    @Throws(
        NoSuchAlgorithmException::class,
        UnrecoverableEntryException::class,
        KeyStoreException::class
    )
    private fun getSecretKey(alias: String): SecretKey {
        return (keyStore?.getEntry(alias, null) as KeyStore.SecretKeyEntry).getSecretKey()
    }

    companion object {

        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private val random: SecureRandom = SecureRandom()
        private const val ANDROID_KEY_STORE = "AndroidKeyStore"
        private const val IV_SEPARATOR = "]"
    }

}