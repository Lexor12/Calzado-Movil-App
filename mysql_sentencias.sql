CREATE DATABASE IF NOT EXISTS calzado;
USE calzado;

-- --------------------------------------------------------

CREATE TABLE calzado (
  id_calzado INT(11) NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(256) NOT NULL,
  marca VARCHAR(256) NOT NULL,
  modelo VARCHAR(256) DEFAULT NULL,
  telefono_contacto VARCHAR(256) DEFAULT NULL,
  precio INT(14) NOT NULL,
  existencias INT(11) NOT NULL DEFAULT 0,
  tipo_calzado VARCHAR(256) DEFAULT NULL,
  material VARCHAR(256) DEFAULT NULL,
  talla VARCHAR(256) DEFAULT NULL,
  color VARCHAR(256) DEFAULT NULL,
  genero VARCHAR(256) DEFAULT NULL,
  PRIMARY KEY (id_calzado)
);

INSERT INTO calzado (id_calzado, nombre, marca, modelo, telefono_contacto, precio, existencias, tipo_calzado, material, talla, color, genero) VALUES
(1, 'Classic Leather', 'Reebok', 'CL-WHT', '3312345679', 1200, 8, 'Tenis', 'Piel', '25', 'Blanco', 'Dama'),
(2, 'Chuck Taylor', 'Converse', 'CT-ALL', '3312345670', 900, 15, 'Tenis', 'Lona', '26', 'Rojo', 'Unisex'),
(3, 'Superstar', 'Adidas', 'SS-BLK', '3312345671', 1300, 12, 'Tenis', 'Piel', '27.5', 'Blanco', 'Caballero'),
(4, 'Oxford Classic', 'Flexi', 'OX-CAF', '3312345672', 1800, 6, 'Zapato', 'Charol', '28', 'Cafe', 'Caballero'),
(5, 'Bota Vaquera', 'Cuadra', 'BV-MIE', '3312345673', 3500, 4, 'Bota', 'Piel', '27', 'Miel', 'Caballero'),
(6, 'Sandalia Verano', 'Scholl', 'SV-AZU', '3312345674', 600, 20, 'Sandalia', 'Sintetico', '24', 'Azul', 'Dama'),
(7, 'Tacon Elegante', 'Andrea', 'TE-NEG', '3312345675', 1100, 9, 'Tacon', 'Gamuza', '23', 'Negro', 'Dama'),
(8, 'Bota Escolar', 'Chabelo', 'BE-NEG', '3312345676', 750, 25, 'Bota', 'Sintetico', '22', 'Negro', 'Infantil'),
(9, 'Runner Pro', 'Puma', 'RP-GRI', '3312345677', 1400, 11, 'Tenis', 'Sintetico', '26.5', 'Gris', 'Unisex'),
(10, 'Air Max 90', 'Nike', 'AM90-BLK', '3312345678', 1500, 10, 'Tenis', 'Sintetico', '27', 'Negro', 'Caballero');

-- --------------------------------------------------------

CREATE TABLE usuario (
  id_usuario INT(11) NOT NULL AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(255) NOT NULL,
  rol INT(11) DEFAULT NULL,
  PRIMARY KEY (id_usuario)
);

-- 5. INSERTS DE LA TABLA: usuario
INSERT INTO usuario (id_usuario, username, password, rol) VALUES
(1, 'admin', 'admin123', 1),
(2, 'trabajador', 'trabajador123', 2);