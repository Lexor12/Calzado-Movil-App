package com.example.calzado

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class tarjeta_calzado : AppCompatActivity() {
    private val REQUEST_CALL : Int=1//Podemos poner cualquier numero siempre y cuando sepamos a que hace referencia
    lateinit var txtNombre: TextView
    lateinit var txtMarca : TextView
    lateinit var txtModelo : TextView
    lateinit var txtTel : TextView
    lateinit var txtExistencias: TextView
    lateinit var txtPrecio: TextView
    lateinit var txtTipoCalzado : TextView
    lateinit var txtMaterial: TextView
    lateinit var txtTalla: TextView
    lateinit var txtColor : TextView
    lateinit var txtGenero: TextView

    lateinit var btnRegresar : Button
    lateinit var llamar : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tarjeta_calzado)
        txtNombre=findViewById<TextView>(R.id.txtNombre)
        txtMarca=findViewById<TextView>(R.id.txtMarca)
        txtModelo=findViewById<TextView>(R.id.txtModelo)
        txtTel=findViewById<TextView>(R.id.txtTelefono)
        txtExistencias=findViewById<TextView>(R.id.txtExistencias)
        txtPrecio=findViewById<TextView>(R.id.txtPrecio)
        txtTipoCalzado=findViewById<TextView>(R.id.txtTipoCalzado)
        txtMaterial=findViewById<TextView>(R.id.txtMaterial)
        txtGenero=findViewById<TextView>(R.id.txtGenero)
        txtTalla=findViewById<TextView>(R.id.txtTalla)
        txtColor=findViewById<TextView>(R.id.txtColor)
        btnRegresar=findViewById<Button>(R.id.btnRegresar)

        val posicion : Int = intent.getIntExtra("position",-1)

        txtNombre.text = "Nombre: ${ListaCalzado.Lista[posicion].nombre}"
        txtMarca.text= "Marca: ${ListaCalzado.Lista[posicion].marca}"
        txtModelo.text = "Modelo: ${ListaCalzado.Lista[posicion].modelo}"
        txtTel.text = "Teléfono de Contacto: ${ListaCalzado.Lista[posicion].telefonoContacto}"
        txtExistencias.text= "Número de existencias: ${ListaCalzado.Lista[posicion].existencias}"
        txtPrecio.text= "Precio: ${ListaCalzado.Lista[posicion].precio}"
        txtTipoCalzado.text = "Tipo de Calzado: ${ListaCalzado.Lista[posicion].tipoCalzado}"
        txtMaterial.text= "Material: ${ListaCalzado.Lista[posicion].material}"
        txtGenero.text= "Género: ${ListaCalzado.Lista[posicion].genero}"
        txtTalla.text = "Talla: ${ListaCalzado.Lista[posicion].talla}"
        txtColor.text= "Color: ${ListaCalzado.Lista[posicion].color}"
        llamar = findViewById<Button>(R.id.btnLlamar)

        llamar.setOnClickListener { llamar(ListaCalzado.Lista[posicion].telefonoContacto) }
        btnRegresar.setOnClickListener {
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun llamar(telefono: String) {
        if (ContextCompat.checkSelfPermission(//Aqui hacemos esto para solicitar permisos
                this,
                Manifest.permission.CALL_PHONE//En el manifiesto se designan los permisos de la APP
            )!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.CALL_PHONE),
                REQUEST_CALL)//Solicitamos que habilite los permisos de llamar
        }
        else{
            //Usaremos los intents ya que tambien sirven para hacer servicios, como la accion de llamar
            val intento = Intent(Intent.ACTION_CALL)//Como lo que queremos es hacer llamada y no simplemente cmbiar de activity, aqui no requeremos poner this
            //Las url permiten identifica un recurso, pero las URI tambien nos sirven para recursos
            intento.data= Uri.parse("tel:${telefono}")//parse es para hacer conversiones de datos o tipos de datos
            //La uri tiene un formato, al igual que las HTTP, por ejemplo aqui es: tel:numeroDeTelefono
            //En este caso, aqui ademas de esto hay que ir al manifest
            //Para concatenar strings podemos usar +
            startActivity(intento)
        }
    }
}
/*
Uri: Significa Uniform Resource Identifier. Es una cadena de texto con un formato estándar
que Android usa para entender con qué recurso va a trabajar.

Uri.parse(...): Es el método que convierte un texto común y corriente en una Uri que Android entienda.
Al ponerle "tel:" al principio, Android sabe automáticamente que lo que sigue es un número de teléfono.
*/