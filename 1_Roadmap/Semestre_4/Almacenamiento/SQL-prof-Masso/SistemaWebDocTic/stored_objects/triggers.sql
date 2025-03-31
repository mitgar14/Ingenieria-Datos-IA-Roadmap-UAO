-- Realizar una alerta que no me permita
-- insertar una nueva contraseña si el usuario insertó la misma contraseña antes.
DELIMITER //

CREATE TRIGGER verificarContrasena
BEFORE INSERT
ON contrasena
FOR EACH ROW
BEGIN
    -- Verificamos si la nueva contraseña ya fue usada antes por el mismo usuario
    IF EXISTS (
        SELECT 1
        FROM contrasena
        WHERE (idUsuario = NEW.idUsuario
        AND contrasena = NEW.contrasena)
    ) THEN
        -- Lanzamos el error si la contraseña ya fue usada
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La contraseña ya ha sido utilizada anteriormente';
    END IF;
END //

DELIMITER ;

-- Test trigger verificarContraseña
INSERT INTO `contrasena` (`contrasena`, `estado`, `fecha`, `idUsuario`) VALUES
('Passw0rd!', 'activa', '2024-08-21 08:00:00', 1);

-- --------------------------------------------------------------------------------------------------------------------------

-- Realizar una alerta que no me permita
-- insertar una nueva valoración si el usuario insertó una valoración <1 y >5.
DELIMITER //

CREATE TRIGGER verificarValoracion
BEFORE INSERT
ON valora
FOR EACH ROW
BEGIN
    -- Verificamos si el usuario insertó una valoración<1 y >5
    IF NEW.valoracion < 1 OR NEW.valoracion > 5 THEN
        -- Lanzamos el error si insertó <1 o >5
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No se puede colocar un número menor que 1 ni mayor que 5, solo números como 1, 2, 3, 4 y 5';
    END IF;
END //

DELIMITER ;

-- Test trigger verificarValoracion
INSERT INTO `valora` (`fechaValoracion`, `idUsuario`, `idDocumento`, `valoracion`) VALUES
('2024-08-21 15:00:00', 1, 1, 6);

-- -------------------------------------------------------------------------------------------------------------------------