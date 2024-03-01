package com.example.shoplist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.bumptech.glide.Glide
import jp.wasabeef.glide.transformations.BlurTransformation
import android.widget.ImageView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_screen)
        val forgotPasswordTextView = findViewById<TextView>(R.id.forgotPasswordTextView)
        val loginButton = findViewById<Button>(R.id.MyButton)
        val registerLink = findViewById<TextView>(R.id.registerLink)
        val imageView = findViewById<ImageView>(R.id.backgroundImageView)

        Glide.with(this)
            .load(R.drawable.image3)
            .transform(BlurTransformation(25)) // Применяем размытие с радиусом 25 (можете изменить по вашему выбору)
            .into(imageView) // Устанавливаем размытое изображение в ImageView

        // Обработчик нажатия на текст "Forgot password?"
        forgotPasswordTextView.setOnClickListener {
            val intent = Intent(this, RecoverActivity::class.java)
            startActivity(intent)
        }

        // Обработчик нажатия на кнопку "Login"
        loginButton.setOnClickListener {
            val intent = Intent(this, AfterLoginActivity::class.java)
            startActivity(intent)
        }

        // Обработчик нажатия на текст "Register"
        registerLink.setOnClickListener {
            val intent = Intent(this, RegiterActivity::class.java)
            startActivity(intent)
        }


    }
}

