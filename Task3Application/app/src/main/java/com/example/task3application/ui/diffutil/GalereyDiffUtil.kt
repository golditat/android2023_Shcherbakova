package com.example.task3application.ui.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.task3application.ui.data.Item

class GalereyDiffUtil(
    private val oldItemsList: ArrayList<Item>,
    private val newItemsList: ArrayList<Item>,
) : DiffUtil.Callback() {

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

        return (oldItem.image == newItem.image) &&
                (oldItem.id == newItem.id)
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldItemsList.get(oldItemPosition)
        val newItem = newItemsList.get(newItemPosition)

        return if (oldItem.love != newItem.love) {
            newItem.love
        } else {
            super.getChangePayload(oldItemPosition, newItemPosition)
        }
    }
}