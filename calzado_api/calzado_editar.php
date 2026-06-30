<?php
require 'db.php';
$data = json_decode(file_get_contents("php://input"), true);

$sentencia = $conn->prepare("UPDATE Calzado SET 
    nombre = ?, marca = ?, modelo = ?, telefono_contacto = ?,
    precio = ?, existencias = ?, tipo_calzado = ?, material = ?,
    talla = ?, color = ?, genero = ? WHERE id_calzado = ?");

$sentencia->bind_param("sssssssssssi",
    $data["nombre"],
    $data["marca"],
    $data["modelo"],
    $data["telefonoContacto"],
    $data["precio"],
    $data["existencias"],
    $data["tipoCalzado"],
    $data["material"],
    $data["talla"],
    $data["color"],
    $data["genero"],
    $data["id_calzado"]
);

if ($sentencia->execute()) {
    echo json_encode(["estado" => true]);
} else {
    echo json_encode(["estado" => false, "error" => $sentencia->error]);
}
$sentencia->close();
$conn->close();
?>