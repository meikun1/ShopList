package com.example.shoplist

import android.os.Bundle
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity

class AfterRegistrationScreen : AppCompatActivity(), GridAdapter.OnItemClickListener {

    private lateinit var gridView: GridView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_registration_screen)

        gridView = findViewById(R.id.grid_view)

        val items = listOf("Испарители одноразовые", "Под-системы", "Жидкости", "Картриджи", "Испарители")
        val images = intArrayOf(R.drawable.hqd, R.drawable.vaip, R.drawable.shisha, R.drawable.kart, R.drawable.ispar)

        val gridAdapter = GridAdapter(this, items, images)
        gridAdapter.setOnItemClickListener(this)

        gridView.adapter = gridAdapter
    }

    override fun onItemClick(position: Int) {
        when (position) {
            0 -> {
                // Логика для открытия категории "Испарители одноразовые"
            }
            1 -> {
                // Логика для открытия категории "Под-системы"
            }
            2 -> {
                // Логика для открытия категории "Жидкости"
            }
            3 -> {
                // Логика для открытия категории "Картриджи"
            }
            4 -> {
                // Логика для открытия категории "Испарители"
            }
            else -> {
                // Действие по умолчанию, если позиция не определена
            }
        }
    }
}
