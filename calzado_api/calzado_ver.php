<?php
require 'db.php';
$resultado = $conn->query("SELECT * FROM Calzado");
$lista = [];
while ($fila = $resultado->fetch_assoc()) { 
    $lista[] = $fila;
}
echo json_encode(["estado" => true, "calzados" => $lista]);
$conn->close();
?>