-- -----------------------------------------------------
-- Insert Statements for SistemaWebDocTICSchema
-- -----------------------------------------------------

-- Seleccionar el esquema de la base de datos
USE `SistemaWebDocTICSchema`;

-- -----------------------------------------------------
-- Tabla: Usuario
-- -----------------------------------------------------
-- Inserciones para la tabla Usuario
INSERT INTO `SistemaWebDocTICSchema`.`Usuario` (`nombreUsuario`, `deptoOrigen`, `ciudadOrigen`, `correoUsuario`, `preguntaSecreta`, `respPregunSecre`) VALUES
('Juan Pérez', 'Antioquia', 'Medellín', 'juan.perez@example.com', 'Nombre de tu primera mascota', 'Firulais'),
('Ana Gómez', 'Cundinamarca', 'Bogotá', 'ana.gomez@example.com', 'Nombre de tu escuela primaria', 'Escuela Central'),
('Carlos Díaz', 'Valle del Cauca', 'Cali', 'carlos.diaz@example.com', 'Nombre de tu mejor amigo', 'Pedro'),
('Marta Ruiz', 'Atlántico', 'Barranquilla', 'marta.ruiz@example.com', 'Lugar de nacimiento de tu madre', 'Hospital Central'),
('Luis Torres', 'Santander', 'Bucaramanga', 'luis.torres@example.com', 'Tu color favorito', 'Azul'),
('Sofía López', 'Bolívar', 'Cartagena', 'sofia.lopez@example.com', 'Nombre de tu primer profesor', 'González'),
('David Fernández', 'Risaralda', 'Pereira', 'david.fernandez@example.com', 'Modelo de tu primer auto', 'Civic'),
('Laura Martínez', 'Tolima', 'Ibagué', 'laura.martinez@example.com', 'Apellido de tu abuelo', 'Martínez'),
('Andrés Gómez', 'Caldas', 'Manizales', 'andres.gomez@example.com', 'Nombre de tu ciudad natal', 'Medellín'),
('Daniela Ramos', 'Huila', 'Neiva', 'daniela.ramos@example.com', 'Nombre de tu primera mascota', 'Rex');
-- -----------------------------------------------------
-- Tabla: Contraseña
-- -----------------------------------------------------
INSERT INTO `Contrasena` (`contrasena`, `estado`, `fecha`, `idUsuario`) VALUES
('Passw0rd!', 'Activa', '2024-08-21 08:00:00', 1),
('Secure123$', 'Activa', '2024-08-21 08:15:00', 2),
('MyPass#456', 'Activa', '2024-08-21 08:30:00', 3),
('Admin@789', 'Inactiva', '2024-08-21 08:45:00', 1),
('Qwerty!23', 'Activa', '2024-08-21 09:00:00', 5),
('Zxcvbnm$1', 'Activa', '2024-08-21 09:15:00', 6),
('Asdfghjkl%', 'Inactiva', '2024-08-21 09:30:00', 7),
('P@ssw0rd2024', 'Activa', '2024-08-21 09:45:00', 8),
('Welcome#2024', 'Activa', '2024-08-21 10:00:00', 9),
('User1234$', 'Inactiva', '2024-08-21 10:15:00', 10);

-- -----------------------------------------------------
-- Tabla: Categoria
-- -----------------------------------------------------
INSERT INTO `Categoria` (`idCategoria`, `nombre`, `subIdCategoria`) VALUES
(1, 'Tecnología', NULL),
(2, 'Data Analysis', 1),
(3, 'Marketing', NULL),
(4, 'Recursos Humanos', NULL),
(5, 'Ventas', NULL),
(6, 'Seguridad', NULL),
(7, 'Operaciones', NULL),
(8, 'Diseño', NULL),
(9, 'Atención al Cliente', 2),
(10, 'Logística', NULL);

-- -----------------------------------------------------
-- Tabla: Documento
-- -----------------------------------------------------
INSERT INTO `Documento` (`tituloDoc`, `visibilidad`, `URL`, `descripcion`, `idCategoria`) VALUES
('Manual de Usuario', 'Publico', 'http://docs.example.com/manual_usuario.pdf', 'Guía completa para el uso del sistema', 1),
('Política de Privacidad', 'Publico', 'http://docs.example.com/politica_privacidad.pdf', 'Detalles sobre el manejo de datos personales', 1),
('Reporte Financiero Q1', 'Privado', 'http://docs.example.com/reporte_financiero_q1.pdf', 'Análisis financiero del primer trimestre', 2),
('Plan de Marketing 2024', 'Privado', 'http://docs.example.com/plan_marketing_2024.pdf', 'Estrategias de marketing para el año 2024', 3),
('Procedimientos de Seguridad', 'Publico', 'http://docs.example.com/procedimientos_seguridad.pdf', 'Protocolos de seguridad internos', 6),
('Informe de Ventas Julio', 'Privado', 'http://docs.example.com/informe_ventas_julio.pdf', 'Resumen de ventas del mes de julio', 5),
('Guía de Integración', 'Publico', 'http://docs.example.com/guia_integracion.pdf', 'Pasos para integrar nuevos empleados', 8),
('Política de Recursos Humanos', 'Publico', 'http://docs.example.com/politica_rrhh.pdf', 'Normativas y políticas del departamento de RRHH', 4),
('Manual Técnico', 'Privado', 'http://docs.example.com/manual_tecnico.pdf', 'Documentación técnica del sistema', 7),
('Presentación Corporativa', 'Publico', 'http://docs.example.com/presentacion_corporativa.pdf', 'Información general sobre la empresa', 9);

-- -----------------------------------------------------
-- Tabla: Publica
-- -----------------------------------------------------
INSERT INTO `Publica` (`fechaPublicacion`, `idUsuario`, `idDocumento`, `rol`) VALUES
('2024-08-21 10:30:00', 1, 1, 'Autor'),
('2024-08-21 10:45:00', 2, 1, 'Coautor'),
('2024-08-21 11:00:00', 3, 1, 'Coautor'),
('2024-08-21 11:15:00', 4, 1, 'Coautor'),
('2024-08-21 11:30:00', 5, 5, 'Autor'),
('2024-08-21 11:45:00', 6, 5, 'Coautor'),
('2024-08-21 12:00:00', 5, 7, 'Autor'),
('2024-08-21 12:15:00', 8, 8, 'Autor'),
('2024-08-21 12:30:00', 9, 9, 'Autor'),
('2024-08-21 12:45:00', 10, 10, 'Autor');

-- -----------------------------------------------------
-- Tabla: Descarga
-- -----------------------------------------------------
INSERT INTO `Descarga` (`fechaDescarga`, `idUsuario`, `idDocumento`) VALUES
('2024-08-21 13:00:00', 1, 5),
('2024-08-21 13:05:00', 2, 5),
('2024-08-21 13:10:00', 3, 2),
('2024-08-21 13:15:00', 3, 3),
('2024-08-21 13:20:00', 5, 4),
('2024-08-21 13:25:00', 6, 6),
('2024-08-21 13:30:00', 7, 7),
('2024-08-21 13:35:00', 8, 8),
('2024-08-21 13:40:00', 9, 9),
('2024-08-21 13:45:00', 10, 10);

-- -----------------------------------------------------
-- Tabla: Mira
-- -----------------------------------------------------
INSERT INTO `Mira` (`fechaMira`, `idUsuario`, `idDocumento`) VALUES
('2024-08-21 14:00:00', 1, 2),
('2024-08-21 14:05:00', 2, 2),
('2024-08-21 14:10:00', 3, 4),
('2024-08-21 14:15:00', 3, 5),
('2024-08-21 14:20:00', 5, 1),
('2024-08-21 14:25:00', 6, 7),
('2024-08-21 14:30:00', 7, 8),
('2024-08-21 14:35:00', 8, 9),
('2024-08-21 14:40:00', 9, 10),
('2024-08-21 14:45:00', 10, 6);

-- -----------------------------------------------------
-- Tabla: Valora
-- -----------------------------------------------------
INSERT INTO `Valora` (`fechaValoracion`, `idUsuario`, `idDocumento`, `valoracion`) VALUES
('2024-08-21 15:00:00', 1, 1, 5),
('2024-08-21 15:05:00', 2, 1, 4),
('2024-08-21 15:10:00', 3, 3, 3),
('2024-08-21 15:15:00', 4, 4, 5),
('2024-08-21 15:20:00', 5, 5, 4),
('2024-08-21 15:25:00', 6, 6, 5),
('2024-08-21 15:30:00', 7, 7, 3),
('2024-08-21 15:35:00', 8, 8, 4),
('2024-08-21 15:40:00', 9, 9, 5),
('2024-08-21 15:45:00', 10, 10, 4);

-- -----------------------------------------------------
-- Tabla: Comentario
-- -----------------------------------------------------
INSERT INTO `Comentario` (`comentario`, `fecha`, `idUsuario`, `idDocumento`, `subidComentario`) VALUES
('Excelente documento, muy útil.', '2024-08-21 16:00:00', 1, 1, NULL),
('Gracias por compartir esta información.', '2024-08-21 16:05:00', 2, 2, 1),
('Tengo una duda sobre la sección 3.', '2024-08-21 16:10:00', 3, 3, NULL),
('Este manual me ha ayudado mucho.', '2024-08-21 16:15:00', 4, 1, 3),
('¿Podrían ampliar más sobre este tema?', '2024-08-21 16:20:00', 5, 4, 2),
('Información muy completa y detallada.', '2024-08-21 16:25:00', 6, 5, NULL),
('Encontré un error en la página 10.', '2024-08-21 16:30:00', 7, 6, NULL),
('¿Cuándo estará disponible la versión actualizada?', '2024-08-21 16:35:00', 8, 7, NULL),
('Me gustaría ver más ejemplos prácticos.', '2024-08-21 16:40:00', 9, 8, NULL),
('Gran aporte, gracias.', '2024-08-21 16:45:00', 10, 9, NULL);
