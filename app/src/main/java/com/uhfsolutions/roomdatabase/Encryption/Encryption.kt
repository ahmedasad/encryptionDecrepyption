package com.uhfsolutions.roomdatabase.Encryption

interface Encryption {
    fun encrypt(value: String?): String?
    fun decrypt(value: String?): String?

    fun <T> encryptList(list: List<T>?): List<T>?
}