package com.example.task5application

import androidx.recyclerview.widget.DiffUtil
import com.example.task5application.database.entity.FillmEntity
import com.example.task5application.model.Film

class FilmDiffUtil(val oldItemsList:ArrayList<FillmEntity>, val newItemsList:ArrayList<FillmEntity> ) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldItemsList.size

    override fun getNewListSize(): Int = newItemsList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItemsList.get(oldItemPosition)
        val newItem = newItemsList.get(newItemPosition)
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItemsList.get(oldItemPosition)
        val newItem = newItemsList.get(newItemPosition)

        return (oldItem.imagesrc == newItem.imagesrc) &&
                (oldItem.id == newItem.id)
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldItemsList.get(oldItemPosition)
        val newItem = newItemsList.get(newItemPosition)

        return if (oldItem.name != newItem.name) {
            newItem.name
        } else {
            super.getChangePayload(oldItemPosition, newItemPosition)
        }
    }
}