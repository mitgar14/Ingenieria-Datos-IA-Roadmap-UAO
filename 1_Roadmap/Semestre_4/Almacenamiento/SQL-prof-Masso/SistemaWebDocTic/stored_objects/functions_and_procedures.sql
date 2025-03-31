-- Función: obtener_media_valoracion
-- Esta función calcula la media de las valoraciones de un documento específico.
-- No se realizaron cambios en esta función ya que funciona correctamente.

DELIMITER //

CREATE FUNCTION obtener_media_valoracion(idDoc INT)
RETURNS DECIMAL(4,2)
DETERMINISTIC
BEGIN
    DECLARE media_valoracion DECIMAL(4,2);

    SELECT AVG(v.valoracion) INTO media_valoracion
    FROM Valora v
    WHERE v.idDocumento = idDoc;

    RETURN media_valoracion;
END //

DELIMITER ;

-- Función: contar_descargas_por_usuario
-- Función eliminada porque mencionaste que no te gustaba.

-- Función: obtener_documento_mas_valorado
-- Esta función devuelve el ID del documento más valorado según las valoraciones promedio.
-- No se realizaron cambios, solo verificaciones de que la lógica del ORDER BY y el LIMIT 1 funciona.

DELIMITER //

CREATE FUNCTION obtener_documento_mas_valorado()
RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE id_documento INT;

    SELECT v.idDocumento
    INTO id_documento
    FROM Valora v
    GROUP BY v.idDocumento
    ORDER BY AVG(v.valoracion) DESC
    LIMIT 1;

    RETURN id_documento;
END //

DELIMITER ;

-- Funcion: obtener_historial_visualizaciones
-- Este procedimiento obtiene el historial de visualizaciones de un documento, mostrando la cantidad de visualizaciones.
-- Funciona correctamente y no se realizaron cambios.

DELIMITER //

CREATE FUNCTION obtener_historial_visualizaciones(idDocumento INT)
RETURNS INT
DETERMINISTIC
BEGIN
	DECLARE cantidadVisualizaciones INT;
    SELECT COUNT(d.tituloDoc)
    INTO cantidadVisualizaciones
    FROM Mira m
    INNER JOIN Documento d ON m.idDocumento = d.idDocumento
    WHERE m.idDocumento = idDocumento;
    
    RETURN cantidadVisualizaciones;
END //

DELIMITER ;

-- Función: contar_comentarios_documento
-- Esta función cuenta cuántos comentarios tiene un documento específico.
-- No se realizaron cambios, ya que la función funciona superficialmente.

DELIMITER //

CREATE FUNCTION contar_comentarios_documento(idDoc INT)
RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE total_comentarios INT;

    SELECT COUNT(c.idComentario)
    INTO total_comentarios
    FROM Comentario c
    WHERE c.idDocumento = idDoc;

    RETURN total_comentarios;
END //

DELIMITER ;

-- Función: contar_publicaciones_por_usuario
-- Esta función cuenta cuántos documentos ha publicado un usuario específico.
-- Funciona correctamente y no se realizaron cambios.

DELIMITER //

CREATE FUNCTION contar_publicaciones_por_usuario(idUsuario INT)
RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE total_publicaciones INT;

    SELECT COUNT(*)
    INTO total_publicaciones
    FROM Publica p
    WHERE p.idUsuario = idUsuario;

    RETURN total_publicaciones;
END //

DELIMITER ;
-- ---------------------------------------------------------------------------------------------
-- Procedimientos almacenados

-- Procedimiento: insertarContrasena
DELIMITER //

-- Crear el procedimiento almacenado
CREATE PROCEDURE insertarContrasena(
    IN p_contrasena VARCHAR(255),
    IN p_estado VARCHAR(10),
    IN p_idUsuario INT
)
BEGIN
    -- Verificamos si el usuario ya tiene una contraseña activa
    IF EXISTS (
        SELECT 1
        FROM contrasena
        WHERE idUsuario = p_idUsuario
        AND estado = 'activa'
    ) THEN
        -- Cambiamos la contraseña activa existente a inactiva
        UPDATE contrasena
        SET estado = 'inactiva'
        WHERE idUsuario = p_idUsuario
        AND estado = 'activa';
    END IF;

    -- Insertamos la nueva contraseña
    INSERT INTO contrasena (contrasena, estado, fecha, idUsuario)
    VALUES (p_contrasena, p_estado, NOW(), p_idUsuario);
END //

DELIMITER ;

-- Llamar al procedimiento almacenado
CALL insertarContrasena('NewPassw0rd!', 'activa', 1);


-- Procedimiento: listar_documentos_por_categoria
-- Este procedimiento lista todos los documentos que pertenecen a una categoría específica.
-- Funciona correctamente y devuelve varias columnas, por lo que no se realizaron cambios.

DELIMITER //

CREATE PROCEDURE listar_documentos_por_categoria(IN categoria VARCHAR(255))
BEGIN
    SELECT d.idDocumento, d.tituloDoc, d.URL
    FROM Categoria c
    INNER JOIN Documento d USING (idCategoria)
    WHERE c.nombre = categoria;
END //

DELIMITER ;


-- Procedimiento: listar_documentos_con_comentarios
-- Este procedimiento lista los documentos que tienen un número de comentarios mayor o igual a la cantidad especificada.
-- Funciona correctamente y el nombre de la función se puede renovar, pero no se ha cambiado en este momento.

DELIMITER //

CREATE PROCEDURE listar_documentos_con_comentarios_seleccionados(IN cantidad INT)
BEGIN
    SELECT d.idDocumento, d.tituloDoc, COUNT(c.idComentario) AS TotalComentarios
    FROM Documento d
    INNER JOIN Comentario c USING (idDocumento)
    GROUP BY d.idDocumento
    HAVING TotalComentarios >= cantidad;
END //

DELIMITER ;


-- Procedimiento: listar_publicaciones_por_fecha
-- Este procedimiento lista todas las publicaciones de documentos realizadas dentro de un rango de fechas dado.
-- Funciona correctamente y permite seleccionar documentos publicados en fechas específicas, no requiere cambios.

DELIMITER //

CREATE PROCEDURE listar_publicaciones_por_fecha(IN fecha_inicio DATE, IN fecha_fin DATE)
BEGIN
    SELECT p.idPublica, p.fechaPublicacion, u.nombreUsuario
    rio, d.tituloDoc
    FROM Publica p
    INNER JOIN Usuario u ON p.idUsuario = u.idUsuario
    INNER JOIN Documento d ON p.idDocumento = d.idDocumento
    WHERE p.fechaPublicacion BETWEEN fecha_inicio AND fecha_fin;
END //

DELIMITER ;


-- Verificaciones de las funciones y procedimientos
-- Verificación de la función: obtener_media_valoracion
SELECT obtener_media_valoracion(1) AS MediaValoracion;

-- Verificación de la función: obtener_documento_mas_valorado
SELECT obtener_documento_mas_valorado() AS DocumentoMasValorado;

-- Verificación de la función: contar_comentarios_documento
SELECT contar_comentarios_documento(2) AS TotalComentarios;

-- Verificación de la función: contar_publicaciones_por_usuario
SELECT contar_publicaciones_por_usuario(8) AS TotalPublicaciones;

-- Verificación del procedimiento: listar_documentos_por_categoria
CALL listar_documentos_por_categoria('Tecnología');

-- Verificación de la función: obtener_historial_visualizaciones
SELECT obtener_historial_visualizaciones(1) AS cantidadHistorial;

-- Verificación del procedimiento: listar_publicaciones_por_fecha
CALL listar_publicaciones_por_fecha('2024-01-01', '2024-12-31');
