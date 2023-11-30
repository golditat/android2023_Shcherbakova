package com.example.task4application

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.task4application.controller.MyActivity


class NotificationHandler {
    var canals:ArrayList<NotificationChannel> = ArrayList()

    fun updateChannel(chanelpriority: Int, ctx: Context):String{
        var newimportance: Int = 0
        when (chanelpriority) {
            0 -> {
                newimportance = NotificationManager.IMPORTANCE_LOW
            }

            1 -> {
                newimportance = NotificationManager.IMPORTANCE_DEFAULT
            }

            2 -> {
                newimportance = NotificationManager.IMPORTANCE_HIGH
            }
        }
        (ctx.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager)?.let { manager ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if(canals.size == 0){
                    val canal0 = NotificationChannel(
                            "task4applicationChannel0",
                            "task 4 channel 0",
                        NotificationManager.IMPORTANCE_LOW
                        )
                    manager.createNotificationChannel(canal0)
                    canals.add(canal0)
                    val canal1 = NotificationChannel(
                        "task4applicationChannel1",
                        "task 4 channel 1",
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                    manager.createNotificationChannel(canal1)
                    canals.add(canal1)
                    val canal2 = NotificationChannel(
                        "task4applicationChannel1",
                        "task 4 channel 1",
                        NotificationManager.IMPORTANCE_HIGH
                    )
                    manager.createNotificationChannel(canal2)
                    canals.add(canal2)
                }
                return canals.get(chanelpriority).id
            }
            return ""
        }
        return ""
    }

     /*fun updateChannel(chanelpriority: Int, ctx: Context){
         (ctx.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager)?.let { manager ->
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                 var newimportance: Int = 0
                 when (chanelpriority) {
                     0 -> {
                         newimportance = NotificationManager.IMPORTANCE_LOW
                     }

                     1 -> {
                         newimportance = NotificationManager.IMPORTANCE_DEFAULT
                     }

                     2 -> {
                         newimportance = NotificationManager.IMPORTANCE_HIGH
                     }
                 }
                 val channel = manager.getNotificationChannel("task4applicationChannel")
                 if(channel != null) {
                     var channelName = channel.name
                     var importance = channel.importance
                     if (importance != newimportance) {
                         manager.deleteNotificationChannel("task4applicationChannel")
                         manager.createNotificationChannel(
                             NotificationChannel(
                                 "task4applicationChannel",
                                 channelName,
                                 newimportance
                             )
                         )
                     }
                 }else{
                     manager.createNotificationChannel(
                         NotificationChannel(
                             "task4applicationChannel",
                             "task 4 channel",
                             newimportance
                         )
                     )
                 }
             }
         }
     }*/

    fun createNotification(ctx:Context, id:Int, chanelpriority:Int, title:String, text:String, deskText:String, privacy:Boolean, detailed:Boolean, buttons:Boolean){
        (ctx.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager)?.let { manager ->
            var idcanal = updateChannel(chanelpriority, ctx)
            var visibility:Int = 0
            if(privacy) {
                    visibility = NotificationCompat.VISIBILITY_PRIVATE
            }else{
                    visibility = NotificationCompat.VISIBILITY_PUBLIC
            }
            val intent1 = Intent(ctx, MyActivity::class.java)
            val pendingIntent1 = PendingIntent.getActivity(ctx, 101, intent1, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)

            val intent2 = Intent(ctx, MyActivity::class.java)
            intent2.putExtra(
                "open_fragment",
                true
            )
            val pendingIntent2 = PendingIntent.getActivity(ctx, 101, intent2, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)

            val notification = NotificationCompat.Builder(ctx, idcanal)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setVisibility(visibility)
            if(detailed){
                notification.setStyle(NotificationCompat.BigTextStyle()
                    .bigText(deskText))
            }
            if(buttons){
                notification.addAction(R.mipmap.ic_launcher, "to app", pendingIntent1)
                    .addAction(R.mipmap.ic_launcher, "to notification settings", pendingIntent2)
            }
            manager.notify(id, notification.build())
        }
    }

}