package com.example.shoplist

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Base64
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom

class DbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "User.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE Users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT," +
                    "password_salt TEXT," +
                    "password_hash TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Users")
        onCreate(db)
    }

    @Throws(NoSuchAlgorithmException::class)
    private fun generateSalt(): String {
        val random = SecureRandom.getInstance("SHA1PRNG")
        val salt = ByteArray(16)
        random.nextBytes(salt)
        return Base64.encodeToString(salt, Base64.DEFAULT)
    }

    @Throws(NoSuchAlgorithmException::class)
    private fun hashPassword(password: String, salt: String): String {
        val passwordWithSalt = password + salt
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(passwordWithSalt.toByteArray())
        return Base64.encodeToString(hash, Base64.DEFAULT)
    }

    fun addUser(username: String, password: String): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        try {
            val salt = generateSalt()
            val hashedPassword = hashPassword(password, salt)
            values.put("username", username)
            values.put("password_salt", salt)
            values.put("password_hash", hashedPassword)
            val result = db.insert("Users", null, values)
            db.close()
            return result != -1L
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            return false
        }
    }

    @SuppressLint("Range")
    fun checkUser(username: String, password: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM Users WHERE username=?",
            arrayOf(username)
        )
        var isValid = false
        if (cursor.moveToFirst()) {
            val salt = cursor.getString(cursor.getColumnIndex("password_salt"))
            val hashedPassword = cursor.getString(cursor.getColumnIndex("password_hash"))
            try {
                val hashedInputPassword = hashPassword(password, salt)
                isValid = hashedInputPassword == hashedPassword
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
        }
        cursor.close()
        db.close()
        return isValid
    }
}
