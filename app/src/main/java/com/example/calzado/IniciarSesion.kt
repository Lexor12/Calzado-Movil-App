package com.example.calzado

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class IniciarSesion : AppCompatActivity() {
    lateinit var usuario : EditText
    lateinit var contra : EditText
    lateinit var boton : Button
    lateinit var sharedPreferences : SharedPreferences
    lateinit var editor : SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        usuario=findViewById<EditText>(R.id.txtUsuario)
        contra=findViewById<EditText>(R.id.txtContra)
        boton=findViewById<Button>(R.id.btnIniciar)
        sharedPreferences=getSharedPreferences("UsuariosPrepreferencias", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        boton.setOnClickListener {
            ingresar()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun ingresar(){
        val username = usuario.text.toString().trim()
        val password = contra.text.toString().trim()
        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Debe llenar los campos.", Toast.LENGTH_SHORT).show()
            return
        }
        // Armamos el JSON que le mandamos al PHP
        val body = JSONObject()
        body.put("username", username)
        body.put("password", password)

        val url = "${ApiConfig.BASE_URL}/login.php"
        val queue = Volley.newRequestQueue(this)

        val request = JsonObjectRequest(
            Request.Method.POST, url, body,
            { response ->
                // Respuesta exitosa del servidor
                val success = response.getBoolean("estado")
                if (success) {
                    val rol = response.getInt("rol")
                    editor.putInt("rol_sesion_actual", rol)
                    editor.apply()

                    Toast.makeText(this, "Accediendo....", Toast.LENGTH_SHORT).show()
                    when (rol) {
                        1 -> startActivity(Intent(this, agregarCalzado::class.java))
                        2 -> startActivity(Intent(this, verCalzado::class.java))
                        else -> Toast.makeText(this, "Rol desconocido.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val mensaje = response.optString("mensaje", "Credenciales inválidas.")
                    Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                // Error de red
                Toast.makeText(this, "Error de conexión: ${error.message}", Toast.LENGTH_LONG)
                    .show()
            }
        )
        queue.add(request)
        }
}