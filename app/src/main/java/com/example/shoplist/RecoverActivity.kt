package com.example.shoplist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecoverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun sendEmail(view: View) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                MailSender.main(arrayOf())
            } catch (e: Exception) {
                e.printStackTrace()
                // Здесь обрабатывайте исключение, например, выводите сообщение об ошибке
            }
        }
    }
}