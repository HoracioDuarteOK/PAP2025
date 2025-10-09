-- Script para agregar columna password y actualizar contraseñas de usuarios
-- Ejecutar este script en pgAdmin (Query Tool sobre la base de datos pap2025)

-- 1. Agregar columna password con valor por defecto
ALTER TABLE usuarios ADD COLUMN IF NOT EXISTS password VARCHAR(100) DEFAULT '1234';

-- 2. Actualizar contraseñas de bibliotecarios
UPDATE usuarios SET password = 'admin123' WHERE email = 'admin@biblioteca.com';
UPDATE usuarios SET password = 'maria123' WHERE email = 'maria@biblioteca.com';
UPDATE usuarios SET password = 'carlos123' WHERE email = 'carlos@biblioteca.com';

-- 3. Actualizar contraseñas de lectores
UPDATE usuarios SET password = 'juan123' WHERE email = 'juan@email.com';
UPDATE usuarios SET password = 'ana123' WHERE email = 'ana@email.com';
UPDATE usuarios SET password = 'pedro123' WHERE email = 'pedro@email.com';
UPDATE usuarios SET password = 'lucia123' WHERE email = 'lucia@email.com';
UPDATE usuarios SET password = 'miguel123' WHERE email = 'miguel@email.com';

-- 4. Hacer la columna NOT NULL
ALTER TABLE usuarios ALTER COLUMN password SET NOT NULL;

-- Verificar que las contraseñas se actualizaron
SELECT email, password, nombre FROM usuarios ORDER BY email;

