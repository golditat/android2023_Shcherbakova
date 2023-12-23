package com.example.task5application.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task5application.FilmDiffUtil
import com.example.task5application.R
import com.example.task5application.controller.MainActivity
import com.example.task5application.database.entity.FillmEntity
import com.example.task5application.model.Film
import com.google.android.material.snackbar.Snackbar

class LikedFilmsAdapter(val context: Context,
                        val items:ArrayList<FillmEntity>,
                        private val listener: OnLikeClickListener
)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_film, parent, false)
        var image:ImageView = view.findViewById(R.id.filmAvatar_iv)
        image.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
        image.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        view.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
        view.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        return FilmViewHolder(view)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val film = items[position]
        (holder as FilmViewHolder).bind(film)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val payload = payloads[0] as Bundle
            for (key in payload.keySet()) {
                when (key) {
                    "image" -> {
                        val imageUrl = payload.getString("image")
                        // функция для изменения картинки
                    }
                    "delete" ->{
                        holder.itemView.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun deleteItem(item: FillmEntity){
        var oldList = items
        var list:ArrayList<FillmEntity> = ArrayList()
        list.addAll(items)
        var position: Int = items.indexOf(item)
        var payloads: Bundle = Bundle()
        payloads.putBoolean("delete", true)
        notifyItemChanged(position, payloads)
        list.remove(item)
        setItems(list)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setItems(list: ArrayList<FillmEntity>){
        val diff = FilmDiffUtil(oldItemsList = items, newItemsList = list)
        val diffResult = DiffUtil.calculateDiff(diff)
        items.clear()
        items.addAll(list)
        diffResult.dispatchUpdatesTo(this)
        var payloads: Bundle = Bundle()
        payloads.putString("delete", "")
        notifyDataSetChanged()
    }

    inner class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val filmAvatarImageView: AppCompatImageView = itemView.findViewById(R.id.filmAvatar_iv)
        private val deleteButtonImageView: AppCompatImageButton = itemView.findViewById(R.id.delete_btn_iv)
        private val likeButtonImageView: AppCompatImageButton = itemView.findViewById(R.id.like_btn_iv)

        fun bind(film: FillmEntity) {
            Glide.with(context).load(film.imagesrc).into(filmAvatarImageView)
            deleteButtonImageView.setOnClickListener {
                listener.onlikedDelete(film)
            }

            likeButtonImageView.setOnClickListener {
                listener.onLikeClickinLiked(film)
            }
        }
    }

}