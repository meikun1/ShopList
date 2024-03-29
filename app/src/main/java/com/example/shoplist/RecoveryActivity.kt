@file:Suppress("DEPRECATION")

package com.example.shoplist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import java.util.*
import kotlin.properties.Delegates

class RecoveryActivity : AppCompatActivity() {

    private lateinit var email: String // Поле для хранения адреса электронной почты
    private var generatedCode: String by Delegates.notNull() // Переменная для хранения сгенерированного кода

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover)

        val emailEditText =
            findViewById<EditText>(R.id.usernameEditText_recover) // Получаем EditText для ввода адреса электронной почты
        val codeEditText =
            findViewById<EditText>(R.id.codeEditText_recover) // Получаем EditText для ввода кода
        val sendCodeText = findViewById<TextView>(R.id.sendCodeText_recover)

        sendCodeText.setOnClickListener {
            email =
                emailEditText.text.toString() // Получаем введенный пользователем адрес электронной почты
            generatedCode = generateRandomCode() // Генерируем случайный код
            SendEmailTask().execute()
        }
    }

    private fun generateRandomCode(): String {
        val random = Random()
        return String.format(
            "%04d",
            random.nextInt(10000)
        ) // Генерируем случайное четырехзначное число
    }

    @SuppressLint("StaticFieldLeak")
    private inner class SendEmailTask : AsyncTask<Void, Void, Boolean>() {
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: Void?): Boolean {
            val username = "gggjhggffc634@gmail.com"
            val password = "mwjtvoyzfykqikcg"
            val to = email // Используем адрес электронной почты, введенный пользователем

            val props = Properties()
            props["mail.smtp.auth"] = "true"
            props["mail.smtp.starttls.enable"] = "true"
            props["mail.smtp.host"] = "smtp.gmail.com"
            props["mail.smtp.port"] = "587"

            val session = Session.getInstance(props, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(username, password)
                }
            })

            return try {
                val message = MimeMessage(session)
                message.setFrom(InternetAddress(username))
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to))
                message.subject = "Test Subject"
                message.setText("Your recovery code is: $generatedCode") // Вставляем сгенерированный код в текст сообщения

                Transport.send(message)

                println("Message sent successfully.")
                true // Успешно отправлено
            } catch (e: MessagingException) {
                e.printStackTrace()
                false // Ошибка отправки
            }
        }

        private var isEmailSent = false // Флаг, указывающий на успешную отправку письма

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: Boolean) {
            super.onPostExecute(result)
            if (isEmailSent) {
                val codeEditText = findViewById<EditText>(R.id.codeEditText_recover)
                val userEnteredCode = codeEditText.text.toString()
                showToast("The email has been sent successfully.")
            } else {
                showToast("Error sending email. Please try again later.")
            }
        }
        @Deprecated("Deprecated in Java")
        fun checkCode1(userEnteredCode: String) {
            if (userEnteredCode == generatedCode) {
                showToast("Code is correct.")
            } else {
                showToast("Incorrect code. Please try again.")
            }
        }
        private fun showToast(message: String) {
            runOnUiThread {
                Toast.makeText(this@RecoveryActivity, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}