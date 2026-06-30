package com.example.calzado

import Calzado
import CalzadoAdapter
import android.app.DownloadManager
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

class agregarCalzado : AppCompatActivity() {
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

    lateinit var btnAgregar : Button
    lateinit var btnVer : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_calzado)
        val toolbar: Toolbar=findViewById<Toolbar>(R.id.toolbar)
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
        btnAgregar=findViewById<Button>(R.id.btnAgregar)
        btnVer=findViewById<Button>(R.id.btnVer)
        val arregloTipoCalzado = resources.getStringArray(R.array.TipoCalzado)
        val arregloMaterial = resources.getStringArray(R.array.Material)
        val arregloGenero = resources.getStringArray(R.array.Genero)
        val arregloTalla = resources.getStringArray(R.array.Talla)
        val arregloColor = resources.getStringArray(R.array.Color)
        val adaptadorTipoCalzado = ArrayAdapter(this,R.layout.item_spinner,R.id.textoSpinner,arregloTipoCalzado)
        val adaptadorMaterial = ArrayAdapter(this,R.layout.item_spinner,R.id.textoSpinner,arregloMaterial)
        val adaptadorGenero = ArrayAdapter(this,R.layout.item_spinner,R.id.textoSpinner,arregloGenero)
        val adaptadorTalla = ArrayAdapter(this,R.layout.item_spinner,R.id.textoSpinner,arregloTalla)
        val adaptadorColor = ArrayAdapter(this,R.layout.item_spinner,R.id.textoSpinner,arregloColor)
        spnTipoCalzado.adapter=adaptadorTipoCalzado
        spnMaterial.adapter=adaptadorMaterial
        spnGenero.adapter=adaptadorGenero
        spnTalla.adapter=adaptadorTalla
        spnColor.adapter=adaptadorColor
        btnVer.setOnClickListener {
            val cambio = Intent(this, verCalzado::class.java);
            startActivity(cambio)
        }
        btnAgregar.setOnClickListener { guardar() }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.agregar->{
                //Toast.makeText(this, "Ya se encuentra en la ventana.", Toast.LENGTH_SHORT).show()
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
    private fun guardar(){
        val nombre = edtNombre.text.toString().trim()
        val marca = edtMarca.text.toString().trim()
        val modelo = edtModelo.text.toString().trim()
        val telefono = edtTel.text.toString().trim()
        val existencias = edtExistencias.text.toString().trim()
        val precio = edtPrecio.text.toString().trim()
        if (nombre.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa el nombre del calzado", Toast.LENGTH_SHORT).show()
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
        if (telefono.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa un teléfono de contacto", Toast.LENGTH_SHORT).show()
            edtTel.requestFocus()
            return
        }
        if (existencias.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa el número de existencias", Toast.LENGTH_SHORT).show()
            edtExistencias.requestFocus()
            return
        }
        if (precio.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa el número de precio", Toast.LENGTH_SHORT).show()
            edtPrecio.requestFocus()
            return
        }
        if (telefono.length!=10 || !esIngresoNumerico(telefono)) {
            Toast.makeText(this, "El teléfono debe tener 10 dígitos numéricos", Toast.LENGTH_LONG).show()
            edtTel.requestFocus()
            return
        }

        val numeroExistencias = existencias.toULongOrNull()
        if (numeroExistencias == null || numeroExistencias < 0u) {
            Toast.makeText(this, "El número de existencias no debe ser negativo", Toast.LENGTH_LONG).show()
            edtExistencias.requestFocus()
            return
        }
        val valorPrecio = precio.toULongOrNull()
        if (valorPrecio == null || valorPrecio < 0u) {
            Toast.makeText(this, "El precio debe ser un numero entero positivo", Toast.LENGTH_LONG).show()
            edtPrecio.requestFocus()
            return
        }
        val calzado= Calzado(
            nombre = edtNombre.text.toString(),
            marca = edtMarca.text.toString(),
            modelo = modelo,
            existencias=existencias,
            telefonoContacto = edtTel.text.toString(),
            tipoCalzado = spnTipoCalzado.selectedItem.toString(),
            material = spnMaterial.selectedItem.toString(),
            talla = spnTalla.selectedItem.toString(),
            color = spnColor.selectedItem.toString(),
            genero = spnGenero.selectedItem.toString(),
            precio=precio
        )
        val body= JSONObject()
        body.put("nombre", calzado.nombre)
        body.put("marca", calzado.marca)
        body.put("modelo", calzado.modelo)
        body.put("telefonoContacto", calzado.telefonoContacto)
        body.put("precio", calzado.precio)
        body.put("existencias", calzado.existencias)
        body.put("tipoCalzado", calzado.tipoCalzado)
        body.put("material", calzado.material)
        body.put("talla", calzado.talla)
        body.put("color", calzado.color)
        body.put("genero", calzado.genero)
        val url = "${ApiConfig.BASE_URL}/calzado_agregar.php"
        val queue= Volley.newRequestQueue(this)
        val request= JsonObjectRequest(Request.Method.POST,url,body,{
            response->
            if(response.getBoolean("estado")){
                calzado.id=response.getInt("id")
                ListaCalzado.Lista.add(calzado)
                Toast.makeText(this, "Calzado agregado exitosamente.", Toast.LENGTH_SHORT).show()
                limpiarCampos()
            }
            else{
                Toast.makeText(this, "Error al agregar.", Toast.LENGTH_SHORT).show()
            }
        },{
            error->
            Toast.makeText(this, "Error de conexión: ${error.message}", Toast.LENGTH_LONG).show()
        })
        val updaterequest = JsonObjectRequest(
            Request.Method.GET, "${ApiConfig.BASE_URL}/calzado_ver.php", null,
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
                }
            },
            { error ->
                Toast.makeText(this, "Error de conexión: ${error.message}", Toast.LENGTH_LONG).show()
            }
        )
        queue.add(request)
        queue.add(updaterequest)
    }
    private fun esIngresoNumerico(ingreso: String): Boolean{
        for (a in ingreso){
            if(!a.isDigit())return false;
        }
        return true;
    }
    private fun limpiarCampos() {
        edtNombre.text.clear()
        edtMarca.text.clear()
        edtModelo.text.clear()
        edtPrecio.text.clear()
        edtTel.text.clear()
        edtExistencias.text.clear()
        spnTipoCalzado.setSelection(0)
        spnMaterial.setSelection(0)
        spnTalla.setSelection(0)
        spnColor.setSelection(0)
        spnGenero.setSelection(0)
    }


}