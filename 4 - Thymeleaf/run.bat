@echo off
echo ========================================================
echo Iniciando Sistema de Ferreteria con Spring Boot y Thymeleaf
echo ========================================================
echo Base de datos: SQLite3 (ferreteria.db)
echo Puerto web: http://localhost:8080
echo API REST: http://localhost:8080/api/productos
echo Catalogo Web: http://localhost:8080/productos
echo ========================================================

call .\mvnw.cmd spring-boot:run
pause
