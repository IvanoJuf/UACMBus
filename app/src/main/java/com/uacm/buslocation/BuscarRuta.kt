package com.uacm.buslocation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class BuscarRuta : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference

    companion object {
        const val MY_CHANNEL_ID = "myChannel"
    }

    private lateinit var boton : Button
    private lateinit var boton2 : Button
    private lateinit var boton3 : Button
    private lateinit var boton4 : Button
    private lateinit var btnCerrar : Button
    private lateinit var btnAviso : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_ruta)

        mDatabase = FirebaseDatabase.getInstance().reference
        //evitamos rotación en pantalla
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        boton = findViewById(R.id.buscar)
        boton2 = findViewById(R.id.btnLleno)
        boton3 = findViewById(R.id.btnTrafico)
        boton4 = findViewById(R.id.btnRetraso)
        btnAviso = findViewById(R.id.aviso)
        btnCerrar = findViewById(R.id.cerrar)


        boton.setOnClickListener {
            val intent = Intent(this,Mapa::class.java)
            startActivity(intent)
        }

        createChannel()
        boton2.setOnClickListener {
            cambiaBanderaLleno()
        }

        boton3.setOnClickListener {
            cambiaBanderaTrafico()
        }

        boton4.setOnClickListener {
            cambiaBanderaRetardo()
        }

        btnAviso.setOnClickListener{
            val intent = Intent(this,AvisoPrivacidad::class.java)
            startActivity(intent)
        }

        btnCerrar.setOnClickListener{
            val intent = Intent(this,Acceso::class.java)
            startActivity(intent)

            val mapAct = mapOf(
                "valor" to "0"
            )
            mDatabase.child("correo").updateChildren(mapAct)
        }
    }

    fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                MY_CHANNEL_ID,
                "MySuperChannel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "BusLocation"
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

    public fun cambiaBanderaRetardo(){
        var mapAct = mapOf(
            "valor" to "1"
        )
        //Actualizo el valor a 1
        mDatabase.child("bandera").updateChildren(mapAct)
    }

    public fun cambiaBanderaLleno(){
        var mapAct = mapOf(
            "valor" to "2"
        )
        //Actualizo el valor a 0
        mDatabase.child("bandera").updateChildren(mapAct)
    }

    public fun cambiaBanderaTrafico(){
        var mapAct = mapOf(
            "valor" to "3"
        )
        //Actualizo el valor a 0
        mDatabase.child("bandera").updateChildren(mapAct)
    }
}