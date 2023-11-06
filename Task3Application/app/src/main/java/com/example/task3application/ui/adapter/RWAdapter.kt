package com.example.task3application.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.task3application.R
import com.example.task3application.databinding.ItemDateBinding
import com.example.task3application.ui.controllers.MainActivity
import com.example.task3application.ui.data.GalereyRepository
import com.example.task3application.ui.data.Item
import com.example.task3application.ui.data.ItemDate
import com.example.task3application.ui.diffutil.GalereyDiffUtil
import com.example.task3application.ui.fragments.DetailInfoFragment
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable


class RWAdapter(
    private val itemCount: Int,
    private val context: Context,
    val fragmentManager: FragmentManager
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Serializable {

    val items = GalereyRepository(itemCount, context)
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_DATE_ITEM = 1

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_galerey, parent, false)
                PhotoViewHolder(view, this, fragmentManager, context)
            }
            VIEW_TYPE_DATE_ITEM -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_date, parent, false)
                DateHeaderViewHolder(view, ItemDateBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int = items.items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PhotoViewHolder -> {
                val newsItem = items.items.get(position) as Item
                holder.bindItem(newsItem, position)
            }
            is DateHeaderViewHolder -> {
                val dateHeaderItem = items.items.get(position) as ItemDate
                holder.bindDate(dateHeaderItem)
            }
        }
    }
    override fun getItemViewType(position: Int): Int {
        return if (items.items.get(position) is ItemDate) {
            VIEW_TYPE_DATE_ITEM
        } else {
            VIEW_TYPE_ITEM
        }
    }

    fun deleteItem(item:Item){
        var oldList = items.items
        var list:ArrayList<Item> = ArrayList()
        list.addAll(items.items)
        var position: Int = items.items.indexOf(item)
        list.remove(item)
        setItems(list)
        var viewActivity = context as MainActivity
        val view:View = viewActivity.supportFragmentManager.fragments.get(fragmentManager.fragments.size-1).requireView()
        val snackbar = Snackbar.make(view, "Элемент удален", Snackbar.LENGTH_LONG)
        snackbar.setAction("Отменить") {
            setItems(oldList)
        }
        snackbar.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(list: ArrayList<Item>) {
        val diff = GalereyDiffUtil(oldItemsList = items.items, newItemsList = list)
        val diffResult = DiffUtil.calculateDiff(diff)
        items.items.clear()
        items.items.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    class PhotoViewHolder(itemView: View, val adapter: RWAdapter, val fragmentManager: FragmentManager, val context: Context) : RecyclerView.ViewHolder(itemView){
        fun bindItem(item: Item,position: Int) {
            //viewBinding.run {

            var photoIv:ImageView = itemView.findViewById(R.id.photo_iv)
                photoIv.setImageDrawable(item.image)
            photoIv.transitionName = "photoTransition_$position"
            if(!item.number){
                var count = 0
                var imageButton: ImageButton = itemView.findViewById(R.id.imageButton)
                photoIv.setOnLongClickListener{
                    if(count%2 ==0) {
                        imageButton.visibility = View.VISIBLE
                        imageButton.setOnClickListener {
                            val list:ArrayList<Item> = ArrayList()
                            list.addAll(adapter.items.items)
                            adapter.deleteItem(item)
                            var viewActivity = context as MainActivity
                            val view:View = viewActivity.supportFragmentManager.fragments.get(fragmentManager.fragments.size-1).requireView()
                            val snackbar = Snackbar.make(view, "Элемент удален", Snackbar.LENGTH_LONG)
                            snackbar.setAction("Отменить") {
                                adapter.setItems(list)
                            }
                            snackbar.show()
                        }
                    }else{
                        imageButton.visibility = View.INVISIBLE
                    }
                    return@setOnLongClickListener true
                }
            }else{
            }
            var loveBtn:Button = itemView.findViewById(R.id.love_btn)
            if(item.love){
                loveBtn.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    null,
                    ContextCompat.getDrawable(
                        itemView.context,
                        android.R.drawable.btn_star_big_on
                    )
                )
            }else{
                loveBtn.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    null,
                    ContextCompat.getDrawable(
                        itemView.context,
                        android.R.drawable.btn_star_big_off
                    )
                )
                item.love = false
            }
                loveBtn.setOnClickListener {
                    item.love = !item.love
                    adapter.notifyItemChanged(position)
                }
                photoIv.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putSerializable("ITEM", item)
                    bundle.putString("TRANSITIONNAME", photoIv.transitionName)
                    val fragment = DetailInfoFragment()
                    fragment.arguments = bundle
                    fragment.setSharedElementEnterTransition(
                        TransitionInflater.from(context).inflateTransition(android.R.transition.move)
                    )
                    fragment.setExitTransition(
                        TransitionInflater.from(context).inflateTransition(android.R.transition.fade)
                    )
                    fragment.setSharedElementReturnTransition(
                        TransitionInflater.from(context).inflateTransition(android.R.transition.move)
                    )
                    fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .addSharedElement(photoIv, "photoTransition_$position")
                        .commit()

            }
        }
    }

    class DateHeaderViewHolder(itemView: View, private val  binding: ItemDateBinding) : RecyclerView.ViewHolder(itemView) {
        fun bindDate(item: ItemDate) {
            //binding.run {
            var dateTw:TextView = itemView.findViewById(R.id.date_tw)
                dateTw.text = item.date
                Log.e("dateSet", item.date)
            //}
        }
    }
}
