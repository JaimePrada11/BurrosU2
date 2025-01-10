CREATE DATABASE IF NOT EXISTS BurrosU2;

USE BurrosU2;

CREATE TABLE IF NOT EXISTS Dueños(
	IDDueño INT PRIMARY KEY AUTO_INCREMENT,
    Nombre VARCHAR(255) NOT NULL,
    Cedula VARCHAR(255) NOT NULL    
);

CREATE TABLE IF NOT EXISTS Razas(
	IDRaza INT PRIMARY KEY AUTO_INCREMENT,
    Nombre VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS Burros(
    IDBurro INT PRIMARY KEY AUTO_INCREMENT,
    Nombre VARCHAR(255) NOT NULL,
    Edad INT NOT NULL,
    IDRaza INT NOT NULL,
    IDDueño INT NOT NULL,
    FOREIGN KEY (IDRaza) REFERENCES Razas(IDRaza),
    FOREIGN KEY (IDDueño) REFERENCES Dueños(IDDueño)    
);

CREATE TABLE IF NOT EXISTS Competencias(
    IDCompetencia INT PRIMARY KEY AUTO_INCREMENT,
    Nombre VARCHAR(255) NOT NULL,
    Fecha DATE NOT NULL,
    Lugar VARCHAR(255) NOT NULL,
    Estado BOOLEAN DEFAULT 0
);

CREATE TABLE IF NOT EXISTS BurrosCompetencia(
	IDCompetencia INT,
	IDBurro INT,
    Puesto INT NULL,
	PRIMARY KEY(IDCompetencia, IDBurro),
    FOREIGN KEY (IDBurro) REFERENCES Burros(IDBurro),
    FOREIGN KEY (IDCompetencia) REFERENCES Competencias(IDCompetencia)
 );

CREATE TABLE IF NOT EXISTS Participantes(
    IDParticipante INT PRIMARY KEY AUTO_INCREMENT,
    Nombre VARCHAR(255) NOT NULL,
    Cedula VARCHAR(255) NOT NULL,
    SaldoDisponible DECIMAL(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS Apuestas (
    IDApuesta INT AUTO_INCREMENT PRIMARY KEY, 
    IDBurro INT NOT NULL,
    IDCompetencia INT NOT NULL,
    IDParticipante INT NOT NULL,
    MontoApostado DECIMAL(10, 2) NOT NULL,
    Estado ENUM('Pendiente', 'Ganada', 'Perdida') DEFAULT 'Pendiente',
    FOREIGN KEY (IDBurro) REFERENCES Burros(IDBurro),
    FOREIGN KEY (IDCompetencia) REFERENCES Competencias(IDCompetencia),
    FOREIGN KEY (IDParticipante) REFERENCES Participantes(IDParticipante)
);

DELIMITER //

CREATE TRIGGER ActualizarApuestas
AFTER UPDATE ON Competencias
FOR EACH ROW
BEGIN
    DECLARE ganador INT;

    IF NEW.Estado = 1 THEN

        SELECT IDBurro INTO ganador
        FROM BurrosCompetencia
        WHERE IDCompetencia = NEW.IDCompetencia
          AND Puesto = 1;

        UPDATE Apuestas
        SET Estado = CASE 
			WHEN IDBurro = ganador THEN 'Ganada'  ELSE 'Perdida'                    
		END
        WHERE IDCompetencia = NEW.IDCompetencia;
    END IF;
END;
//

DELIMITER ;

DELIMITER //

CREATE TRIGGER verificaSaldo
BEFORE INSERT ON Apuestas
FOR EACH ROW
BEGIN
    DECLARE saldoActual DECIMAL(10,2);

    SELECT SaldoDisponible INTO saldoActual
    FROM Participantes
    WHERE IDParticipante = NEW.IDParticipante;

    IF saldoActual < NEW.MontoApostado THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Saldo insuficiente para realizar la apuesta';
    END IF;
END;
//

DELIMITER ;


DELIMITER //

CREATE TRIGGER actualizarSaldo
AFTER UPDATE ON Apuestas
FOR EACH ROW
BEGIN
    IF NEW.Estado = 'Ganada' THEN
        UPDATE Participantes
        SET SaldoDisponible = SaldoDisponible + NEW.MontoApostado
        WHERE IDParticipante = NEW.IDParticipante;
    
    ELSEIF NEW.Estado = 'Perdida' THEN
        UPDATE Participantes
        SET SaldoDisponible = SaldoDisponible - NEW.MontoApostado
        WHERE IDParticipante = NEW.IDParticipante;
    END IF;
END;
//

DELIMITER ;


INSERT INTO Dueños (Nombre, Cedula) VALUES 
('Juan Pérez', '12345678'),
('Ana González', '23456789'),
('Carlos Ramírez', '34567890'),
('Luis Martínez', '45678901'),
('María López', '56789012');

INSERT INTO Razas (Nombre) VALUES 
('Criollo'),
('Andaluz'),
('Burro Catalán'),
('Caribeño'),
('Burro Cordobés');

INSERT INTO Burros (Nombre, Edad, IDRaza, IDDueño) VALUES 
('Rayo', 5, 1, 1),
('Luna', 4, 2, 2),
('Nube', 6, 3, 3),
('Sol', 3, 4, 4),
('Tormenta', 7, 5, 5),
('Viento', 2, 1, 1),
('Estrella', 8, 2, 2),
('Dulce', 4, 3, 3),
('Relámpago', 6, 4, 4),
('Cielo', 3, 5, 5);

INSERT INTO Competencias (Nombre, Fecha, Lugar, Estado) VALUES 
('Competencia 1', '2024-01-10', 'Parque Nacional', 1), 
('Competencia 2', '2024-02-15', 'Hipódromo Ciudad', 1),  
('Competencia 3', '2024-03-20', 'Plaza Mayor', 1),        
('Competencia 4', '2025-02-01', 'Campo Abierto', 0),       
('Competencia 5', '2025-03-10', 'Circuito Rápido', 0);    
   

INSERT INTO BurrosCompetencia (IDCompetencia, IDBurro, Puesto) VALUES 

(1, 1, 1),  -- Rayo en el primer lugar
(1, 5, 2),  -- Luna en el segundo lugar
(1, 6, 3),  -- Nube en el tercer lugar
(1, 9, 4),  -- Sol en el cuarto lugar
(1, 2, 5),

-- Competencia 2 (Completada)
(2, 2, 1),  -- Tormenta en el primer lugar
(2, 4, 2), 
(2, 1, 3),  
(2, 6, 4), 
(2, 3, 5), 
(2, 10, 6), 
(2, 5, 7), 

-- Competencia 3 (Completada)
(3, 9, 1),  -- Relámpago en el primer lugar
(3, 10, 2),  -- Cielo en el segundo lugar
(3, 1, 3),  -- Rayo en el tercer lugar
(3, 2, 4),  -- Luna en el cuarto lugar
(3, 3, 9), 
(3, 4, 8), 
(3, 5, 10), 
(3, 6, 7), 
(3, 7, 6), 
(3, 8, 5), 

-- Competencia 4 (Pendiente)
(4, 3, NULL),  -- Nube en la competencia pendiente
(4, 4, NULL),  -- Sol en la competencia pendiente
(4, 5, NULL),  -- Tormenta en la competencia pendiente
(4, 6, NULL),  

(5, 7, NULL),  -- Estrella en la competencia pendiente
(5, 8, NULL),  -- Dulce en la competencia pendiente
(5, 9, NULL),  -- Relámpago en la competencia pendiente
(5, 10, NULL);  -- Cielo en la competencia pendiente

INSERT INTO Participantes (Nombre, Cedula, SaldoDisponible) VALUES 
('Pedro Sánchez', '67890123', 500.00),
('María Fernández', '78901234', 350.00),
('José Álvarez', '89012345', 600.00),
('Laura Torres', '90123456', 450.00),
('Ricardo Gómez', '01234567', 300.00);

INSERT INTO Apuestas (IDBurro, IDCompetencia, IDParticipante, MontoApostado, Estado) VALUES 
-- Apuestas en competencia 1 (Completadas)
(1, 1, 1, 100.00, 'Ganada'),   -- Pedro Sánchez apostó 100.00 a Rayo y ganó
(5, 1, 2, 50.00, 'Perdida'),   -- María Fernández apostó 50.00 a Tormenta y perdió
(6, 1, 3, 75.00, 'Perdida'),   -- José Álvarez apostó 75.00 a Viento y perdió
(9, 1, 4, 100.00, 'Perdida'),  -- Laura Torres apostó 100.00 a Relámpago y perdió

(2, 2, 5, 150.00, 'Ganada'),   -- Ricardo Gómez apostó 150.00 a Luna y ganó
(4, 2, 2, 200.00, 'Perdida'),  -- María Fernández apostó 200.00 a Sol y perdió
(1, 2, 1, 100.00, 'Perdida'),  -- Pedro Sánchez apostó 100.00 a Rayo y perdió
(6, 2, 3, 125.00, 'Perdida'),  -- José Álvarez apostó 125.00 a Viento y perdió

(9, 3, 4, 80.00, 'Ganada'),    -- Laura Torres apostó 80.00 a Relámpago y ganó
(10, 3, 1, 60.00, 'Perdida'),  -- Pedro Sánchez apostó 60.00 a Cielo y perdió
(1, 3, 5, 90.00, 'Perdida'),   -- Ricardo Gómez apostó 90.00 a Rayo y perdió

(3, 4, 2, 50.00, 'Pendiente'), -- María Fernández apostó 50.00 a Nube (resultado pendiente)
(4, 4, 3, 100.00, 'Pendiente'), -- José Álvarez apostó 100.00 a Sol (resultado pendiente)
(5, 4, 4, 75.00, 'Pendiente'), -- Laura Torres apostó 75.00 a Tormenta (resultado pendiente)

(7, 5, 1, 80.00, 'Pendiente'), -- Pedro Sánchez apostó 80.00 a Estrella (resultado pendiente)
(8, 5, 2, 120.00, 'Pendiente'),-- María Fernández apostó 120.00 a Dulce (resultado pendiente)
(9, 5, 3, 60.00, 'Pendiente'), -- José Álvarez apostó 60.00 a Relámpago (resultado pendiente)
(10, 5, 4, 100.00, 'Pendiente');-- Laura Torres apostó 100.00 a Cielo (resultado pendiente)

