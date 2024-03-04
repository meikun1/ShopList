package com.example.shoplist

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.bumptech.glide.Glide
import jp.wasabeef.glide.transformations.BlurTransformation
import android.widget.ImageView


class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_screen)
        val forgotPasswordTextView = findViewById<TextView>(R.id.forgotPasswordTextView)
        val loginButton = findViewById<Button>(R.id.MyButton)
        val registerLink = findViewById<TextView>(R.id.registerLink)
        val imageView = findViewById<ImageView>(R.id.backgroundImageView)

        // Инициализируем SharedPreferences
        sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE)

        Glide.with(this)
            .load(R.drawable.image3)
            .transform(BlurTransformation(25))
            .into(imageView)

        // Проверяем состояние "Запомнить меня" при загрузке активности
        val rememberMe = sharedPreferences.getBoolean("remember_me", false)
        if (rememberMe) {
            // Если "Запомнить меня" выбрано, переходим на AfterLoginActivity
            val intent = Intent(this, AfterLoginActivity::class.java)
            startActivity(intent)
            finish() // Закрываем текущую активность
        }

        // Обработчик нажатия на текст "Forgot password?"
        forgotPasswordTextView.setOnClickListener {
            val intent = Intent(this, RecoverActivity::class.java)
            startActivity(intent)
        }

        // Обработчик нажатия на кнопку "Login"
        loginButton.setOnClickListener {
            val intent = Intent(this, AfterLoginActivity::class.java)
            startActivity(intent)

            // Сохраняем состояние "Запомнить меня" в SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putBoolean("remember_me", true)
            editor.apply()
        }

        // Обработчик нажатия на текст "Register"
        registerLink.setOnClickListener {
            val intent = Intent(this, RegiterActivity::class.java)
            startActivity(intent)
        }
    }
}
