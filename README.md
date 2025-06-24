# Reserva-ms
## Descripción

Este proyecto implementa un microservicio REST para crear, consultar y actualizar **Reservas**, con manejo de DTOs, validaciones y pruebas unitarias.

## Tecnologías

- Java 17
- Spring Boot
- Spring Data JPA
- Maven
- JUnit + Mockito para testing

## Requisitos

- Java 17 o superior
- Maven 3.6+
- Base de datos configurada (PostgreSQL o H2 para pruebas)

## Cómo levantar el proyecto

1. Clonar el repositorio:
   ```git clone https://github.com/hayleencc/vitalboost-reserva-ms.git```


2. Configurar la base de datos con las variables del archivo de ejemplo y crearlo en:
   ```src/main/java/resources/application-dev.properties```.


3. Construir y ejecutar con Maven:
   ```mvn clean install```
   ```mvn spring-boot:run```


4. La aplicación arrancará en `http://localhost:8082`
5. Para probar los endpoints existentes puedes acceder a ```http://localhost:8082/swagger-ui/index.html```

## Testing
Se puede usar el comando ```mvn test```


## Estructura del proyecto
Accede a la carpeta `src` y dentro encontraras la carpeta `main`. Luego accedes a `java/org/vb` teniendo como carpetas principales:
- `controller/`: Archivos de configuración de la aplicación
- `controller/`: Controladores REST
- `service/`: Lógica de negocio
- `repository/`: Acceso a datos
- `dto/`: Objetos de transferencia de datos, tanto para request como response
- `model/`: Entidades JPA
- `exception/`: Manejador de excepciones
- `resources/`: Configuracion para la base de datos



En la carpeta `test/java/org/vb` encontraras una carpeta con las pruebas unitarias por capas:
- `service/`: Pruebas unitarias de la capa de servicio