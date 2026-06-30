package com.example.calzado

import Calzado
import CalzadoAdapter
import CalzadoEliminarAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class eliminarCalzado : AppCompatActivity() {
    lateinit var recy: RecyclerView
    lateinit var adaptador: CalzadoEliminarAdapter
    lateinit var botonEliminar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_eliminar_calzado)
        var toolbar: Toolbar=findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.overflowIcon?.setTint(resources.getColor(R.color.white, theme))
        botonEliminar=findViewById<Button>(R.id.btnEliminar)
        botonEliminar.setOnClickListener {
            borrarElementos()
        }
        cargarDatos()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun borrarElementos() {
        if (adaptador.itemsSeleccionados.isEmpty()) {
            Toast.makeText(this, "No seleccionaste ningún item.", Toast.LENGTH_SHORT).show()
            return
        }
        val url = "${ApiConfig.BASE_URL}/calzado_eliminar.php"
        val queue = Volley.newRequestQueue(this)

        val idsArray = JSONArray()
        for (calzado in adaptador.itemsSeleccionados) idsArray.put(calzado.id)

        val body = JSONObject()
        body.put("ids", idsArray)
        val request= JsonObjectRequest(Request.Method.POST,url,body,{
            response->
            if(response.getBoolean("estado")){
                adaptador.eliminarSeleccionados()
                Toast.makeText(this, "Elementos eliminados correctamente.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error al eliminar.", Toast.LENGTH_SHORT).show()
            }
        },{
            error->                Toast.makeText(this, "Error de conexión: ${error.message}", Toast.LENGTH_SHORT).show()
        })
        queue.add(request)
    }
    fun cargarDatos(){
        val url = "${ApiConfig.BASE_URL}/calzado_ver.php"
        val queue = Volley.newRequestQueue(this)

        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                if (response.getBoolean("estado")) {
                    val arreglo = response.getJSONArray("calzados")
                    ListaCalzado.Lista.clear()
                    for (i in 0 until arreglo.length()) {
                        val obj = arreglo.getJSONObject(i)
                        ListaCalzado.Lista.add(
                            Calzado(
                                id = obj.getInt("id_calzado"),
                                nombre = obj.getString("nombre"),
                                marca = obj.getString("marca"),
                                modelo = obj.getString("modelo"),
                                telefonoContacto = obj.getString("telefono_contacto"),
                                precio = obj.getString("precio"),
                                existencias = obj.getString("existencias"),
                                tipoCalzado = obj.getString("tipo_calzado"),
                                material = obj.getString("material"),
                                talla = obj.getString("talla"),
                                color = obj.getString("color"),
                                genero = obj.getString("genero")
                            )
                        )
                    }
                    recy = findViewById<RecyclerView>(R.id.rv)
                    recy.layoutManager = LinearLayoutManager(this)
                    // Pasamos directamente la lista mutable global
                    adaptador = CalzadoEliminarAdapter(ListaCalzado.Lista)
                    recy.adapter = adaptador
                }
            },
            { error ->
                Toast.makeText(this, "Error de conexión: ${error.message}", Toast.LENGTH_LONG).show()
            }
        )
        queue.add(request)
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
                //Toast.makeText(this, "Ya se encuentra en la ventana.", Toast.LENGTH_SHORT).show()
            }
            R.id.exit->{
                val sharedPreferences = getSharedPreferences("UsuariosPrepreferencias", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.remove("rol_sesion_actual").apply()
                startActivity(Intent(this, IniciarSesion::class.java))
                finish()
            }
            R.id.creador->{
                val cambio = Intent(this, creador::class.java)
                startActivity(cambio)
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
        return super.onCreateOptionsMenu(menu)
    }
}