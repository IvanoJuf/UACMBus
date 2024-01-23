package com.uacm.buslocation

import android.app.PendingIntent
import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {

    //genera la notificación
    //adjunta la notificacion creada con la vista personalizada
    //muestra la notificacion

    //Se crea el token unico para enviar notificaciones a un usiario en especifico
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val notification = remoteMessage.notification
        val title: String = notification!!.title!!
        val msg: String = notification.body!!

        sendNotification(title, msg)
    }

    private fun sendNotification(title: String, msg: String) {
        val intent = Intent(this, BuscarRuta::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            MyNotification.NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val notification = MyNotification(this, MyNotification.CHANNEL_ID_NOTIFICATIONS)
        notification.build(R.drawable.ic_launcher_foreground, title, msg, pendingIntent)
        notification.addChannel("Notificaciones")
        notification.createChannelGroup(
            MyNotification.CHANNEL_GROUP_GENERAL,
            R.string.notification_channel_group_general
        )
        notification.show(MyNotification.NOTIFICATION_ID)
    }

}