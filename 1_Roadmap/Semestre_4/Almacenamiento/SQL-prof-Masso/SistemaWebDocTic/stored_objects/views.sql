-- Vista 1: Usuarios que tienen más de una contraseña registrada
CREATE VIEW Vista_Usuarios_Multiples_Contrasenas AS
SELECT U.nombreUsuario, COUNT(C.idContrasena) AS total_contrasenas
FROM SistemaWebDocTICSchema.Usuario U
JOIN SistemaWebDocTICSchema.Contrasena C ON U.idUsuario = C.idUsuario
GROUP BY U.idUsuario
HAVING total_contrasenas > 1;

-- SELECT para ver los datos de la vista Vista_Usuarios_Multiples_Contrasenas
SELECT * FROM Vista_Usuarios_Multiples_Contrasenas;

-- Vista 2: Documentos más descargados
CREATE VIEW Vista_Documentos_Mas_Descargados AS
SELECT D.tituloDoc, COUNT(DG.idDescarga) AS total_descargas
FROM SistemaWebDocTICSchema.Documento D
JOIN SistemaWebDocTICSchema.Descarga DG ON D.idDocumento = DG.idDocumento
GROUP BY D.idDocumento
HAVING total_descargas > 1;

-- SELECT para ver los datos de la vista Vista_Documentos_Mas_Descargados
SELECT * FROM Vista_Documentos_Mas_Descargados;

-- Vista 3: Usuarios con más de dos publicaciones
CREATE VIEW Vista_Usuarios_Mas_Publicaciones AS
SELECT U.nombreUsuario, COUNT(P.idPublica) AS total_publicaciones
FROM SistemaWebDocTICSchema.Usuario U
JOIN SistemaWebDocTICSchema.Publica P ON U.idUsuario = P.idUsuario
GROUP BY U.idUsuario
HAVING total_publicaciones > 2;

-- SELECT para ver los datos de la vista Vista_Usuarios_Mas_Publicaciones
SELECT * FROM Vista_Usuarios_Mas_Publicaciones;

-- Vista 5: Documentos mejor valorados con más de 4 estrellas en promedio
CREATE VIEW Vista_Documentos_Mejor_Valorados AS
SELECT D.tituloDoc, AVG(V.valoracion) AS valoracion_promedio
FROM SistemaWebDocTICSchema.Documento D
JOIN SistemaWebDocTICSchema.Valora V ON D.idDocumento = V.idDocumento
GROUP BY D.idDocumento
HAVING valoracion_promedio > 4;

-- SELECT para ver los datos de la vista Vista_Documentos_Mejor_Valorados
SELECT * FROM Vista_Documentos_Mejor_Valorados;

-- Vista 4: Documentos públicos más visualizados
CREATE VIEW Vista_Documentos_Publicos_Mas_Visualizados AS
SELECT D.tituloDoc, COUNT(M.idMira) AS total_visualizaciones
FROM SistemaWebDocTICSchema.Documento D
JOIN SistemaWebDocTICSchema.Mira M ON D.idDocumento = M.idDocumento
WHERE D.visibilidad = 'Publico'
GROUP BY D.idDocumento
HAVING total_visualizaciones > 1;

-- SELECT para ver los datos de la vista Vista_Documentos_Publicos_Mas_Visualizados
SELECT * FROM Vista_Documentos_Publicos_Mas_Visualizados;
