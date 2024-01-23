package com.uacm.buslocation

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class AvisoPrivacidad : AppCompatActivity() {

    private lateinit var txtAviso : TextView
    private val link:String? = "https://drive.google.com/file/d/1lSDQNMJiPJU5a-riABiG-eM4_AK3S7nv/view?usp=sharing"
    private lateinit var btnRegresa : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aviso_privacidad)
        //evitamos rotación en pantalla
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        txtAviso = findViewById(R.id.aviso)
        btnRegresa = findViewById(R.id.regresar)

        txtAviso.setOnClickListener {
            val pagina : Uri = Uri.parse(link)
            val intent = Intent(Intent.ACTION_VIEW,pagina)
            startActivity(intent)
        }

        btnRegresa.setOnClickListener {
            val intent = Intent(this,BuscarRuta::class.java)
            startActivity(intent)
        }

    }


}