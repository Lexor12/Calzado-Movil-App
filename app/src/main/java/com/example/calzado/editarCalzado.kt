package com.example.calzado

import Calzado
import CalzadoAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import kotlin.collections.get
import kotlin.compareTo

class editarCalzado : AppCompatActivity() {
    lateinit var edtNombre: EditText
    lateinit var edtMarca : EditText
    lateinit var edtModelo : EditText
    lateinit var edtTel : EditText
    lateinit var edtExistencias: EditText
    lateinit var edtPrecio: EditText
    lateinit var spnTipoCalzado : Spinner
    lateinit var spnMaterial: Spinner
    lateinit var spnTalla: Spinner
    lateinit var spnColor : Spinner
    lateinit var spnGenero: Spinner

    lateinit var btnSiguiente: Button
    lateinit var btnAnterior: Button
    lateinit var btnModificar : Button

    lateinit var adaptadorTipoCalzado:ArrayAdapter<String>
    lateinit var adaptadorMaterial :ArrayAdapter<String>
    lateinit var adaptadorGenero :ArrayAdapter<String>
    lateinit var adaptadorTalla :ArrayAdapter<String>
    lateinit var adaptadorColor:ArrayAdapter<String>
    var indexActual:Int=0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_editar_calzado)

        var toolbar: Toolbar=findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.overflowIcon?.setTint(resources.getColor(R.color.white, theme))

        edtNombre=findViewById<EditText>(R.id.edtNombre)
        edtMarca=findViewById<EditText>(R.id.edtMarca)
        edtModelo=findViewById<EditText>(R.id.edtModelo)
        edtTel=findViewById<EditText>(R.id.edtTelefono)
        edtExistencias=findViewById<EditText>(R.id.edtExistencias)
        edtPrecio=findViewById<EditText>(R.id.edtPrecio)
        spnTipoCalzado=findViewById<Spinner>(R.id.spnTipoCalzado)
        spnMaterial=findViewById<Spinner>(R.id.spnMaterial)
        spnGenero=findViewById<Spinner>(R.id.spnGenero)
        spnTalla=findViewById<Spinner>(R.id.spnTalla)
        spnColor=findViewById<Spinner>(R.id.spnColor)
        btnSiguiente=findViewById<Button>(R.id.btnSiguiente)
        btnAnterior=findViewById<Button>(R.id.btnAnterior)
        btnModificar=findViewById<Button>(R.id.btnModificar)
        val arregloTipoCalzado = resources.getStringArray(R.array.TipoCalzado)
        val arregloMaterial = resources.getStringArray(R.array.Material)
        val arregloGenero = resources.getStringArray(R.array.Genero)
        val arregloTalla = resources.getStringArray(R.array.Talla)
        val arregloColor = resources.getStringArray(R.array.Color)
        adaptadorTipoCalzado = ArrayAdapter(this,R.layout.item_spinner,R.id.textoSpinner,arregloTipoCalzado)
        adaptadorMaterial = ArrayAdapter(this,R.layout.item_spinner,R.id.textoSpinner,arregloMaterial)
        adaptadorGenero = ArrayAdapter(this,R.layout.item_spinner,R.id.textoSpinner,arregloGenero)
        adaptadorTalla = ArrayAdapter(this,R.layout.item_spinner,R.id.textoSpinner,arregloTalla)
        adaptadorColor = ArrayAdapter(this,R.layout.item_spinner,R.id.textoSpinner,arregloColor)
        spnTipoCalzado.adapter=adaptadorTipoCalzado
        spnMaterial.adapter=adaptadorMaterial
        spnGenero.adapter=adaptadorGenero
        spnTalla.adapter=adaptadorTalla
        spnColor.adapter=adaptadorColor


        cargarDatos()
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
                //Toast.makeText(this, "Ya se encuentra en la ventana.", Toast.LENGTH_SHORT).show()
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
    private fun cargarCalzado(calzado: Calzado){
        edtNombre.setText(calzado.nombre)
        edtMarca.setText(calzado.marca)
        edtModelo.setText(calzado.modelo)
        edtTel.setText(calzado.telefonoContacto)
        edtExistencias.setText(calzado.existencias)
        edtPrecio.setText(calzado.precio)
        var index = adaptadorTipoCalzado.getPosition(calzado.tipoCalzado)?: -1
        if(index>=0){
            spnTipoCalzado.setSelection(index)
        }
        index = adaptadorMaterial.getPosition(calzado.material)?: -1
        if(index>=0){
            spnMaterial.setSelection(index)
        }
        index = adaptadorTalla.getPosition(calzado.talla)?: -1
        if(index>=0){
            spnTalla.setSelection(index)
        }
        index = adaptadorColor.getPosition(calzado.color)?: -1
        if(index>=0){
            spnColor.setSelection(index)
        }
        index = adaptadorGenero.getPosition(calzado.genero)?: -1
        if(index>=0){
            spnGenero.setSelection(index)
        }
    }
    private fun modificarCalzado() {
        val calzado = ListaCalzado.Lista[indexActual]

        val nombre = edtNombre.text.toString().trim()
        val marca = edtMarca.text.toString().trim()
        val modelo = edtModelo.text.toString().trim()
        val telefono = edtTel.text.toString().trim()
        val existencias = edtExistencias.text.toString().trim()
        val precio = edtPrecio.text.toString().trim()

        val tipoCalzado = spnTipoCalzado.selectedItem.toString()
        val material = spnMaterial.selectedItem.toString()
        val talla = spnTalla.selectedItem.toString()
        val color = spnColor.selectedItem.toString()
        val genero = spnGenero.selectedItem.toString()


        if (nombre.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa el nombre", Toast.LENGTH_SHORT).show()
            edtNombre.requestFocus()
            return
        }
        if (marca.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa la marca", Toast.LENGTH_SHORT).show()
            edtMarca.requestFocus()
            return
        }
        if (modelo.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa el modelo", Toast.LENGTH_SHORT).show()
            edtModelo.requestFocus()
            return
        }
        if (telefono.length != 10 || !esIngresoNumerico(telefono)) {
            Toast.makeText(this, "El teléfono debe tener exactamente 10 dígitos numéricos", Toast.LENGTH_LONG).show()
            edtTel.requestFocus()
            return
        }
        val numeroExistencias = existencias.toIntOrNull()
        if (numeroExistencias == null || numeroExistencias < 0) {
            Toast.makeText(this, "El número de existencias no debe ser negativo", Toast.LENGTH_LONG).show()
            edtExistencias.requestFocus()
            return
        }
        val valorPrecio = precio.toIntOrNull()
        if (valorPrecio == null || valorPrecio < 0) {
            Toast.makeText(this, "El precio no debe ser negativo", Toast.LENGTH_LONG).show()
            edtPrecio.requestFocus()
            return
        }


        val body = JSONObject()
        body.put("id_calzado", calzado.id)
        body.put("nombre", nombre)
        body.put("marca", marca)
        body.put("modelo", modelo)
        body.put("telefonoContacto", telefono)
        body.put("precio", precio)
        body.put("existencias", existencias)
        body.put("tipoCalzado", tipoCalzado)
        body.put("material", material)
        body.put("talla", talla)
        body.put("color", color)
        body.put("genero", genero)

        val url = "${ApiConfig.BASE_URL}/calzado_editar.php"
        val queue = Volley.newRequestQueue(this)

        val request = JsonObjectRequest(Request.Method.POST, url, body, { response ->
            if (response.getBoolean("estado")) {

                calzado.nombre = nombre
                calzado.marca = marca
                calzado.modelo = modelo
                calzado.telefonoContacto = telefono
                calzado.tipoCalzado = tipoCalzado
                calzado.material = material
                calzado.talla = talla
                calzado.color = color
                calzado.genero = genero
                calzado.precio = precio
                calzado.existencias = existencias

                Toast.makeText(this, "Elemento modificado correctamente.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error al editar según el servidor.", Toast.LENGTH_SHORT).show()
            }
        }, { error ->
            Toast.makeText(this, "Error de conexión: ${error.message}", Toast.LENGTH_SHORT).show()
        })

        queue.add(request)
    }

    private fun esIngresoNumerico(ingreso: String): Boolean {
        return ingreso.all { it.isDigit() }
    }
    fun cargarDatos(){
        indexActual = 0
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
                    // DESPUÉS
                    if(ListaCalzado.Lista.isNotEmpty()){
                        cargarCalzado(ListaCalzado.Lista[indexActual])

                        btnSiguiente.setOnClickListener {
                            if(indexActual == ListaCalzado.Lista.size - 1){
                                indexActual = 0
                            } else {
                                indexActual++
                            }
                            cargarCalzado(ListaCalzado.Lista[indexActual])
                        }

                        btnAnterior.setOnClickListener {
                            if(indexActual == 0){
                                indexActual = ListaCalzado.Lista.size - 1
                            } else {
                                indexActual--
                            }
                            cargarCalzado(ListaCalzado.Lista[indexActual])
                        }

                        btnModificar.setOnClickListener { modificarCalzado() }
                    }
                }
            },
            { error ->
                Toast.makeText(this, "Error de conexión: ${error.message}", Toast.LENGTH_LONG).show()
            }
        )
        queue.add(request)
    }
}