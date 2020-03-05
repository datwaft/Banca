# Proyecto I, Programación IV: Banca

## Autores:
- David Guevara Sánchez
- Mario Arguello Borge

## Comandos para configurar Maven

### Generar el proyecto
```
mvn archetype:generate -DgroupId={project-packaging} -DartifactId={project-name} -DarchetypeArtifactId={maven-template} -DinteractiveMode=false
```

### Actualizar los plugins
```
mvn versions:display-dependency-updates
mvn versions:use-latest-releases
mvn versions:commit
```
