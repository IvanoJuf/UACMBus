package com.uacm.buslocation

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.util.PatternsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern

class Acceso : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference

    private lateinit var txtRecuperapss: TextView
    private lateinit var btnLogin: Button
    private val link:String? = "https://isi.uacm.edu.mx/"
    private lateinit var txtemail : EditText
    private lateinit var txtpass : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acceso)

        mDatabase = FirebaseDatabase.getInstance().reference
        mDatabase.child("correo").child("valor").get().addOnSuccessListener{
            val res = it.value
            Log.e("El valor:","es:"+res)
            if(res == "1"){
                val intent = Intent(this,BuscarRuta::class.java)
                startActivity(intent)
            }
        }

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        txtRecuperapss = findViewById(R.id.recupera_psswrd)
        btnLogin = findViewById(R.id.buttonLogin)
        txtemail = findViewById(R.id.emailLogin)
        txtpass = findViewById(R.id.TextPassword)

        txtRecuperapss.setOnClickListener{
            val pagina : Uri = Uri.parse(link)
            val intent = Intent(Intent.ACTION_VIEW,pagina)
            startActivity(intent)
        }

        btnLogin.setOnClickListener{

            if(txtemail.text.toString().isEmpty() && txtpass.text.toString().isEmpty()){
                Toast.makeText(this,"Debe llenar todos los campos",Toast.LENGTH_SHORT).show()
            }else if(txtemail.text.toString().isEmpty() || txtpass.text.toString().isEmpty()){
                Toast.makeText(this,"Ddebe llenar todos los campos",Toast.LENGTH_SHORT).show()
            }else if(validaDatos(txtemail.text.toString())){
                val intent = Intent(this,BuscarRuta::class.java)
                startActivity(intent)

                val mapAct = mapOf(
                    "valor" to "1"
                )
                mDatabase.child("correo").updateChildren(mapAct)
            }else{
                Toast.makeText(this,"Debes ingresar con tu correo institucional",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validaDatos(email:String) : Boolean{
        if(Pattern.compile(
                "^"+
                        "[A-ZÁÉÍÓÚÑa-záéíóúñ]{3,30}[.][A-ZÁÉÍÓÚÑa-záéíóúñ]{3,30}" +
                        "([.][A-ZÁÉÍÓÚÑa-záéíóúñ]{3,30})?[@](estudiante)?(alumno)?[.](uacm)[.](edu)[.](mx)(\\s){0,5}"
            ).matcher(email).matches()){
            return true
        }else{
            return false
        }
    }


}