package com.uacm.buslocation

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Registro : AppCompatActivity() {

    private lateinit var boton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        boton = findViewById(R.id.btnRegistro)

        println("Esto es una linea de codigo agregada desde Ivan2")

        println("Nuevo mensaje agregado desde ivan2")

        boton.setOnClickListener{
            val intent = Intent(this,Acceso::class.java)
            startActivity(intent)
        }
    }
}