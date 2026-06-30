<?php
require 'db.php';

$data = json_decode(file_get_contents("php://input"), true);
$ids = $data["ids"] ?? [];   // Array de IDs, ej: [1, 3, 5]

if (empty($ids)) {
    echo json_encode(["estado" => false, "mensaje" => "No se enviaron IDs"]);
    exit();
}

$errores = [];

foreach ($ids as $id) {
    $sentencia = $conn->prepare("DELETE FROM calzado WHERE id_calzado = ?");
    $sentencia->bind_param("i", $id);
    if (!$sentencia->execute()) {
        $errores[] = $id;
    }
    $sentencia->close();
}

if (empty($errores)) {
    echo json_encode(["estado" => true]);
} else {
    echo json_encode(["estado" => false, "ids_fallidos" => $errores]);
}

$conn->close();
?>