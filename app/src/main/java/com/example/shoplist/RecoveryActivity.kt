@file:Suppress("DEPRECATION")

package com.example.shoplist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import java.util.*
import kotlin.coroutines.CoroutineContext
import kotlin.properties.Delegates

class RecoveryActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var email: String
    private var generatedCode: String by Delegates.notNull()

    private fun generateRandomCode(): String {
        val random = Random()
        return String.format("%04d", random.nextInt(10000))
    }

    private var isCodeCorrect = false // Флаг для отслеживания успешной проверки кода

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover)

        job = Job()

        val emailEditText = findViewById<EditText>(R.id.usernameEditText_recover)
        val codeEditText = findViewById<EditText>(R.id.codeEditText_recover)
        val sendCodeText = findViewById<TextView>(R.id.sendCodeText_recover)
        val checkCodeText = findViewById<TextView>(R.id.CheckCodeText_recover)
        val registerButton = findViewById<Button>(R.id.MyButton_recover) // Найти кнопку Register

        generatedCode = ""

        sendCodeText.setOnClickListener {
            email = emailEditText.text.toString()

            if (!isValidEmail(email)) {
                showToast("Invalid email address.")
                return@setOnClickListener
            }

            generatedCode = generateRandomCode()

            launch {
                val result = sendEmail()

                if (result) {
                    showToast("Email sent successfully. Check your inbox for the recovery code.")
                } else {
                    showToast("Error sending email. Please try again later.")
                }
            }
        }

        checkCodeText.setOnClickListener {
            val userEnteredCode = codeEditText.text.toString()
            checkCode(userEnteredCode)
        }

        registerButton.setOnClickListener {
            if (isCodeCorrect) { // Проверяем флаг перед переходом
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                showToast("Please enter the correct code first.")
            }
        }
    }

    private suspend fun sendEmail(): Boolean = withContext(Dispatchers.IO) {
        val username = "gggjhggffc634@gmail.com"
        val password = "mwjtvoyzfykqikcg"
        val to = email

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

        return@withContext try {
            val message = MimeMessage(session)
            message.setFrom(InternetAddress(username))
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to))
            message.subject = "Test Subject"
            message.setText("Your recovery code is: $generatedCode")

            Transport.send(message)

            println("Message sent successfully.")
            true
        } catch (e: MessagingException) {
            e.printStackTrace()
            false
        }
    }

    private fun checkCode(userEnteredCode: String) {
        isCodeCorrect = if (userEnteredCode == generatedCode) {
            showToast("Code is correct.")
            true // Устанавливаем флаг в true при успешной проверке
        } else {
            showToast("Incorrect code. Please try again.")
            false // Устанавливаем флаг в false при неправильном коде
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@RecoveryActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
