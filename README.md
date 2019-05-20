## OMERO DSL Plugin

The OMERO DSL plugin for [Gradle](https://gradle.org/) provides a plugin named `dsl`.
This plugin manages the reading of `*.ome.xml` mappings and compilation of `.vm` velocity templates.

### Build

To build the plugin run:
```shell
$./gradlew build
```

To publish the plugin locally run:
```shell
$./gradlew publishToMavenLocal
```

### Usage

Include the following to the top of your _build.gradle_ file:

```groovy
buildscript {
    repositories {
        jcenter()
        mavenLocal()
    }

    dependencies {
        classpath 'org.openmicroscopy:dsl:1.0'
    }
} 

apply plugin: 'org.openmicroscopy.omero-dsl-plugin'
```

### Configuring plugin

The omero-dsl plugin introduces the `dsl {}` block to define configuration options for
_Velocity_ and _SemanticType_ processing.  

omero-dsl supports two kinds of code generation, source code (multiple files) and resource code (single file).

To generate resource (single) files or source code (multiple files), start by adding a `singleFile { }` or `multiFile { }`
block to `dsl {}`. Inside the `singleFile` or `multiFile` block you can add **one** or **more** inner
blocks. Inner blocks that you add can be named anything you want and will result in tasks being created with
the prefix `generate[YOUR BLOCK NAME HERE]`.

Code Example:

```groovy
dsl {
    multiFile {
        java {
            template "object.vm"
            outputDir  "build/psql/java" //psql is the default database type
            formatOutput = { st ->
                "${st.getPackage()}/${st.getShortname()}.java"
            }
        }
    }
}
```

This will add a task `generateJava` under the group `omero-dsl` in gradle _(to print a list of available gradle
tasks run `gradle tasks` in a terminal)_.

Resource Example:

```groovy
dsl {
    singleFile {
        hibernate {
            template "cfg.vm"
            outputFile "build/psql/resources/hibernate.cfg.xml"
        }
    }
}
```

This will add a task `generateHibernate` under the group `omero-dsl` in gradle _(to print a list of available gradle
tasks run `gradle tasks` in a terminal)_.

_If you are using Intellij, refresh the _Gradle Toolbar_ and the task will appear in the list once the IDE completes 
its work._

For convenience, you can set the following three properties if templates and generated files reside in similar locations.
For example:

```groovy
dsl {
    // -- Optional ---
    mappingFiles fileTree(dir: "src/main/resources/mappings", include: '**/*.ome.xml')
    outputDir file("build/psql")
    // ---------------

    code {
        java {
            template "object.vm" // This will be searched for in 'src/main/resources/templates'
            outputDir "java" // This will output to build/psql/java
            formatOutput { st ->
                "${st.getPackage()}/${st.getShortname()}.java"
            }
        }
    }
    
    resource {
        hibernate {
            template "cfg.vm" // This will be searched for in 'src/main/resources/templates'
            outputFile "resources/hibernate.cfg.xml" // This will output to build/psql/resources/hibernate.cfg.xml
        }
    }
}
```

###### (Optional)
In order to configure the [VelocityEngine](https://velocity.apache.org), add the `velocity`
extension to your _build.gradle_ file, for example:

```groovy
dsl {
    velocity {
        resource_loader = 'file'
        resource_loader_class = ['file.resource.loader.class': 'org.apache.velocity.runtime.resource.loader.FileResourceLoader']
        file_resource_loader_path = 'src/main/resources/templates'
        file_resource_loader_cache = false
    }
}
```

### `dsl` Properties

| Property      | Type           | Default              | Description                                    |
|---------------|----------------|----------------------|------------------------------------------------|
| omeXmlFiles   | FileCollection | jar'd .ome.xml files | `.ome.xml` mapping files in project            |
| outputDir     | File           |           -          | Base output directory to place generated files |

### `multiFile` Properties

| Property name | type           | Default value | Description                                                                      |
|---------------|----------------|---------------|----------------------------------------------------------------------------------|
| outputDir     | File           |       -       | Output directory to generate files in                                            |
| formatOutput  | Closure        |       -       | Closure that receives a `SemanticType` object for tweaking generated files names |
| database      | String         |       -       | Path to .properties file describing type for the database engine syntax to use   |                                                   |
| template      | File           |       -       | Velocity file, can be absolute or filename to search through `dsl.templateFiles` |
| omeXmlFiles   | FileCollection |       -       | Collection of `.ome.xml` files, overrides `dsl.mappingFiles` if set              |

### `singleFile` Properties

| Property name | type           | Default value | Description                                                                      |
|---------------|----------------|---------------|----------------------------------------------------------------------------------|
| outputFile    | File           |       -       | File to generate                                                                 |
| database      | String         |       -       | Path to .properties file describing type for the database engine syntax to use   |                                                    |
| template      | File           |       -       | Velocity file, can be absolute or filename to search through `dsl.templateFiles` |
| omeXmlFiles   | FileCollection |       -       | Collection of `.ome.xml` files, overrides `dsl.mappingFiles` if set              |

### Gradle Task

Additional configurations to the `dsl` extension add a new task 

| Type                | Description                                       |
| ------------------- | ------------------------------------------------- |
| tasks.DslBaseTask   | Generates Java source from ome.xml and .vm files  |

If, like in the examples above, you create configurations `javaModels` and `sqlModels`, these tasks will run
before `compileJava`.

| Task name   | Depends On        |
| ----------- | ----------------- |
| compileJava | processJavaModels |
| compileJava | processSqlModels  |
