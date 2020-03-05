# Proyecto I, Programación IV: Banca

## Autores:
- David Guevara Sánchez
- Mario Arguello Borge

## FAQ

### La página web se ve pocha, ¿cómo lo arreglo? (netbeans)

1. Dale clic derecho al servidor y dale a _Properties_.
2. Ve a _Run_
3. Cambia _Browser_ a Google Chrome.

Esto sucede porque Internet Explorer no soporta muy bien _FlexBox_.

![Annotation 2020-03-04 212000](https://user-images.githubusercontent.com/37723586/75944850-5426ff00-5e5e-11ea-9fbd-ff6895c9bbf6.png)

## Comandos para instalar Maven

### Windows
```bash
choco install maven
```

### Linux
```bash
sudo apt-get install maven
```

## Comandos para configurar Maven

### Generar el proyecto
```bash
mvn archetype:generate -DgroupId=banca -DartifactId=banca -DarchetypeArtifactId=maven-archetype-webapp -DinteractiveMode=false
```

### Actualizar los plugins
```bash
mvn versions:display-dependency-updates
mvn versions:use-latest-releases
mvn versions:commit
```

### Ejecutar el proyecto desde la consola
```bash
mvn jetty:run
```

### Compilar archivo .war desde la consola
```bash
mvn clean package
```
