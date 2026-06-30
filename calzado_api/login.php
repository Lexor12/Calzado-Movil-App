<?php
require 'db.php';
$data = json_decode(file_get_contents("php://input"), true);
$username = $data["username"] ?? "";
$password = $data["password"] ?? "";
if (empty($username) || empty($password)) {
    echo json_encode(["estado" => false, "mensaje" => "Debe llenar los campos."]);
    exit();
}

$sentencia = $conn->prepare("SELECT rol FROM Usuario WHERE username = ?");
$sentencia->bind_param("s", $username);
$sentencia->execute();
$result = $sentencia->get_result();
$usuarioExiste = $result->num_rows > 0;
$sentencia->close();

$sentencia = $conn->prepare("SELECT rol FROM Usuario WHERE password = ?");
$sentencia->bind_param("s", $password);
$sentencia->execute();
$result = $sentencia->get_result();
$passwordExiste = $result->num_rows > 0;
$sentencia->close();

if (!$usuarioExiste && !$passwordExiste) {
    echo json_encode(["estado" => false, "mensaje" => "Credenciales invalidas."]);
    $conn->close();
    exit();
}
if (!$usuarioExiste) {
    echo json_encode(["estado" => false, "mensaje" => "Usuario incorrecto."]);
    $conn->close();
    exit();
}
if (!$passwordExiste) {
    echo json_encode(["estado" => false, "mensaje" => "Contrasena incorrecta."]);
    $conn->close();
    exit();
}

$sentencia = $conn->prepare("SELECT rol FROM Usuario WHERE username = ? AND password = ?");
$sentencia->bind_param("ss", $username, $password);
$sentencia->execute();
$result = $sentencia->get_result();
if ($result->num_rows === 0) {
    echo json_encode(["estado" => false, "mensaje" => "Credenciales invalidas."]);
    $sentencia->close();
    $conn->close();
    exit();
}
$fila = $result->fetch_assoc();
echo json_encode(["estado" => true, "rol" => (int)$fila["rol"]]);
$sentencia->close();
$conn->close();
?>