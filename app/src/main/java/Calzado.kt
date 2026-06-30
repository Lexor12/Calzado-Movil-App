data class Calzado(
    //Campos de Texto (EditText)
    var id: Int?=null,
    var nombre: String,
    var marca: String,
    var modelo: String,
    var telefonoContacto: String,
    var precio: String,
    var existencias: String,
    var tipoCalzado: String,      // Spinner 1 (Tenis, Zapato, Bota...)
    var material: String,         // Spinner 2 (Sintético, Piel, Gamuza...)
    var talla: String,            // Spinner 3 (24, 25, 26, 27...)
    var color: String,            // Spinner 4 (Negro, Blanco, Café...)
    var genero: String            // Spinner 5 (Caballero, Dama, Unisex)
)