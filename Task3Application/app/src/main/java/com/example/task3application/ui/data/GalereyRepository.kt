package com.example.task3application.ui.data

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.task3application.R
import java.io.File
import java.io.Serializable
import kotlin.random.Random

data class GalereyRepository(
    var itemCount:Int,
    var context: Context
) {
    var items:ArrayList<Item> = getListItems()

    fun getListItems():ArrayList<Item>{
        var list:ArrayList<Item> = ArrayList()
        if(itemCount != 0) {
            var cnt= 0;
            for (i in 1..itemCount) {
                if(cnt%8 == 0 && cnt != 0){
                    var itemDate = ItemDate(i.toString() + " day ago", 1,0,context, itemCount<= 12)
                    list.add(itemDate)
                }
                var imageid: Int = Random.nextInt(1, 6)
                var item: Item = Item(imageid, cnt, context, itemCount<=12)
                list.add(item)
                cnt++
            }
        }
        return list
    }

    fun addNewItems(count:Int){
        Log.e("",count.toString())
        Log.e("", items.size.toString())
        if (count != 0) {
            var cnt = items.size
            if (items.isNotEmpty()) {
                for (i in 0 until count) {
                    val newList: ArrayList<Item> = ArrayList()
                    val randomIndexAtList: Int = Random.nextInt(0, items.size)
                    val imageId: Int = Random.nextInt(1, 6)
                    val item: Item = Item(imageId, cnt, context, itemCount<=12)
                    for (j in 0 until items.size) {
                        if (j == randomIndexAtList) {
                            newList.add(item)
                        }
                        newList.add(items[j])
                    }
                    items = newList
                    cnt++
                }
            } else {
                var cnt = 0
                for (i in 0 until count) {
                    val newList: ArrayList<Item> = ArrayList()
                    var randomIndexAtList: Int
                    if(items.size == 0){
                        val imageId: Int = Random.nextInt(1, 6)
                        val item: Item = Item(imageId, cnt, context, itemCount<=12)
                        newList.add(item)
                    }else {
                        randomIndexAtList = Random.nextInt(0, items.size)
                        val imageId: Int = Random.nextInt(1, 6)
                        val item: Item = Item(imageId, cnt, context, itemCount<= 12)
                        if(randomIndexAtList == items.size){
                            newList.addAll(items)
                            newList.add(item)
                        }else {
                            for (j in 0 until items.size) {
                                if (j == randomIndexAtList) {
                                    newList.add(item)
                                }
                                newList.add(items.get(j))
                            }
                        }
                    }
                    items = newList
                    cnt++
                }
            }
        }
    }
}
open class Item(
    var imageid:Int,
    var id:Int,
    var context:Context,
    var number:Boolean
) : Serializable{

    var image: Drawable = (ContextCompat.getDrawable(context, ImageRepo().list.get(imageid)) ?: ContextCompat.getDrawable(context, R.drawable.r1))!!
    var itemid:Int = id;
    var love:Boolean = false
    var vertical = number

}
class ItemDate (
    var date:String,
    imageid: Int,
    id: Int,
    context: Context,
    number:Boolean
) : Item(imageid, id, context, number){
    var itemdate = date
}
class ImageRepo{
    var list:ArrayList<Int> = arrayListOf(R.drawable.r1,
        R.drawable.r2,
        R.drawable.r3,
        R.drawable.r4,
        R.drawable.r5,
        R.drawable.r6)
}
