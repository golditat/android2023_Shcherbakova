package com.example.task5application.adapters

import com.example.task5application.database.entity.FillmEntity

interface OnLikeClickListener {
    fun onLikeClickinfilm(item: FillmEntity)
    fun onLikeClickinLiked(item: FillmEntity)
    fun onfilmDelete(item: FillmEntity)
    fun onlikedDelete(item: FillmEntity)
}
