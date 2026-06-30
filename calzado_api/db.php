<?php
$host="localhost";
$user="root";
$pass="";
$db="calzado";

$conn=new mysqli($host,$user,$pass,$db);

if($conn->connect_error){
    http_response_code(500);
    echo json_encode(["Error"=>"Conexión fallida: " . $conn->connect_error]);
    exit();
}

header("Content-Type: application/json");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST,GET");
header("Access-Control-Allow-Headers: Content-Type");
?>
