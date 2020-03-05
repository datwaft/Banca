# Proyecto I, Programación IV: Banca

## Autores:
- David Guevara Sánchez
- Mario Arguello Borge

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
mvn archetype:generate -DgroupId={project-packaging} -DartifactId={project-name} -DarchetypeArtifactId={maven-template} -DinteractiveMode=false
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
