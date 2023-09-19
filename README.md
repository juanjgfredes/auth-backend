# AuthApp Backend

Backend de una aplicación basica de autenticación para probar con Angular
## Instalacion 

### Requerimientos generales:
- [Java 17](https://www.java.com/en/)
- [Maven](https://maven.apache.org/download.cgi)
- [MongoDb](https://www.mongodb.com/)
- [Docker](https://www.docker.com/)

### Instalación:
1. Clona el repositorio
   ```
   git clone https://github.com/JuannFredes/angular-auth-app.git
   ```
2. Estando en la carpeta `auth-backend` en la terminal ejecute el siguiente comando:
   ```
   docker compose up -d
   ```
3. Ejecuta el siguiente comando en la terminal para compilar el proyecto
   ```
   mvn clean package
   ```
4. Finalmente, para iniciar el proyecto ejecuta
   ```
   java -jar target/escuela-0.0.1-SNAPSHOT.jar
   ```
