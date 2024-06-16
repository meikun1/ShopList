package com.example.shoplist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class GridAdapter(private val context: Context, private val items: List<String>, private val images: IntArray) : BaseAdapter() {

    private var itemClickListener: OnItemClickListener? = null

    // Устанавливаем слушатель для кликов на элементы
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.itemClickListener = listener
    }

    // Возвращает количество элементов
    override fun getCount(): Int {
        return items.size
    }

    // Возвращает элемент по позиции
    override fun getItem(position: Int): Any {
        return items[position]
    }

    // Возвращает ID элемента по позиции
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // Создает представление для каждого элемента
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val gridViewItem: View
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        if (convertView == null) {
            // Создаем новое представление
            gridViewItem = inflater.inflate(R.layout.grid_item, null)
        } else {
            // Используем существующее представление
            gridViewItem = convertView
        }

        // Получаем TextView и ImageView из макета
        val textViewItem = gridViewItem.findViewById<TextView>(R.id.item_name)
        val imageViewItem = gridViewItem.findViewById<ImageView>(R.id.item_image)

        // Устанавливаем данные
        textViewItem.text = items[position]
        imageViewItem.setImageResource(images[position])

        // Обработчик кликов на элементе
        gridViewItem.setOnClickListener {
            itemClickListener?.onItemClick(position)
        }

        return gridViewItem
    }

    // Интерфейс для обработки кликов
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}
