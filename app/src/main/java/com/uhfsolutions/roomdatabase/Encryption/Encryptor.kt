package com.uhfsolutions.roomdatabase.Encryption

import android.os.Build
//import org.apache.commons.codec.binary.Base64
import android.util.Base64
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import javax.crypto.*


class Encryptor {
    private val TRANSFORMATION = "AES/GCM/NoPadding"
    private val ANDROID_KEY_STORE = "AndroidKeyStore"
    private val IV_SEPARATOR = "]"
    val SAMPLE_ALIAS = "MYALIAS22"
    private val random: SecureRandom = SecureRandom()
    private var encryption: ByteArray? = null
    private var iv: ByteArray? =null
    private val initVector = "Random123456"
    var secretKey: SecretKey? = null


    @Throws(Exception::class)
    private fun generateIVBytes(cipher: Cipher): ByteArray {
        val ivBytes = ByteArray(12)
        random.nextBytes(ivBytes)
        return ivBytes
    }

    @Throws(
        NoSuchAlgorithmException::class,
        NoSuchPaddingException::class,
        InvalidKeyException::class,
        BadPaddingException::class,
        IllegalBlockSizeException::class
    )
    fun encryptText(textToEncrypt: String, key: SecretKey?): String? {
        var result: String? = ""
        val cipher: Cipher = Cipher.getInstance(TRANSFORMATION)
            try {
                val ivBytes = generateIVBytes(cipher)
                cipher.init(Cipher.ENCRYPT_MODE, key)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        iv = cipher.getIV()
        val ivString: String
        ivString = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.encodeToString(iv,Base64.DEFAULT)
        } else {
            Base64.encodeToString(iv, Base64.DEFAULT)
        }
        result = ivString + IV_SEPARATOR
        val en: ByteArray = cipher.doFinal(textToEncrypt.toByteArray())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            result += Base64.encodeToString(en,Base64.DEFAULT)
        } else {
            result += Base64.encodeToString(en, Base64.DEFAULT)
        }
        return result
    }
}