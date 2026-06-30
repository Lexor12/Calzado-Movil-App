package com.example.calzado

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class creador : AppCompatActivity() {
    var privilegioActual : Int =-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_creador)
        var toolbar: Toolbar=findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.overflowIcon?.setTint(resources.getColor(R.color.white, theme))
        val sharedPreferences = getSharedPreferences("UsuariosPrepreferencias", Context.MODE_PRIVATE)
        privilegioActual=sharedPreferences.getInt("rol_sesion_actual",-1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.agregar->{

                val cambio = Intent(this, agregarCalzado::class.java)
                startActivity(cambio)
            }
            R.id.ver->{
                val cambio = Intent(this, verCalzado::class.java)
                startActivity(cambio)
            }
            R.id.editar->{
                val cambio = Intent(this, editarCalzado::class.java)
                startActivity(cambio)
            }
            R.id.eliminar->{
                val cambio = Intent(this, eliminarCalzado::class.java)
                startActivity(cambio)
            }
            R.id.exit->{
                val sharedPreferences = getSharedPreferences("UsuariosPrepreferencias", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.remove("rol_sesion_actual").apply()
                startActivity(Intent(this, IniciarSesion::class.java))
                finish()
            }
            R.id.creador->{
                //Toast.makeText(this, "Ya se encuentra en la ventana.", Toast.LENGTH_SHORT).show()
            }
            R.id.contacto->{
                val cambio = Intent(this, contacto::class.java)
                startActivity(cambio)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        if(privilegioActual!=1){
            menu?.findItem(R.id.agregar)?.isVisible=false
            menu?.findItem(R.id.editar)?.isVisible=false
            menu?.findItem(R.id.eliminar)?.isVisible=false
        }
        return super.onCreateOptionsMenu(menu)
    }
}