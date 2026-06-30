<?php
require 'db.php';

$data = json_decode(file_get_contents("php://input"), true);

$sentencia = $conn->prepare("INSERT INTO Calzado(nombre, marca, modelo, telefono_contacto, precio, existencias, tipo_calzado, material, talla, color, genero) 
    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

$sentencia->bind_param("sssssssssss",
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
    $data["genero"]
);

if ($sentencia->execute()) {
    echo json_encode(["estado" => true, "id" => $conn->insert_id]);
} else {
    echo json_encode(["estado" => false, "error" => $sentencia->error]);
}
$sentencia->close();
$conn->close();
?>