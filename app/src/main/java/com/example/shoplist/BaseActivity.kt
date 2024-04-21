package com.example.shoplist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

// Добавляем точку с запятой в конце
// Убираем импорт ParentClass, если он не используется
class BaseActivity : AppCompatActivity() {
    // Убираем скобки после AppCompatActivity
    private var database: FirebaseDatabase? = null // Добавляем модификатор доступа private
    override fun onCreate(savedInstanceState: Bundle?) { // Исправляем сигнатуру метода onCreate
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance()
    }
}

