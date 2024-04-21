package com.example.shoplist

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private var usernameEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var passwordEditText1: EditText? = null
    private var registerButton: Button? = null
    private var databaseReference: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regiter)

        // Инициализация элементов управления
        usernameEditText = findViewById(R.id.usernameEditText_register)
        passwordEditText = findViewById(R.id.passwordEditText_register)
        passwordEditText1 = findViewById(R.id.passwordEditText_register1)
        registerButton = findViewById(R.id.MyButton_register)

        // Получение ссылки на базу данных Firebase
        databaseReference = FirebaseDatabase.getInstance().reference.child("users")

        // Обработчик нажатия на кнопку регистрации
        registerButton!!.setOnClickListener(View.OnClickListener { registerUser() })
    }

    // Метод для регистрации пользователя
    private fun registerUser() {
        val username = usernameEditText!!.text.toString().trim { it <= ' ' }
        val password = passwordEditText!!.text.toString().trim { it <= ' ' }
        val password1 = passwordEditText1!!.text.toString().trim { it <= ' ' }

        // Проверка на совпадение паролей
        if (password != password1) {
            // Вывод сообщения о несовпадении паролей
            // Здесь можно использовать Toast или Snackbar для отображения сообщения пользователю
            return
        }

        // Создание объекта пользователя
        val user = User(username, password)

        // Запись пользователя в базу данных Firebase
        databaseReference!!.push().setValue(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User registration successful")
                    // Здесь можно добавить дополнительные действия после успешной регистрации
                } else {
                    Log.e(
                        TAG,
                        "User registration failed",
                        task.exception
                    )
                    // Здесь можно обработать ошибку при регистрации
                }
            }
    }

    companion object {
        private const val TAG = "RegisterActivity"
    }
}
