plugins {
    kotlin("jvm") version "2.1.0"
    id("earth.terrarium.cloche") version "0.18.10"
}

repositories {
    cloche.librariesMinecraft()
    mavenCentral()
    cloche {
        main()
        mavenNeoforgedMeta()
        mavenNeoforged()
        mavenFabric()
    }
}

group = "dev.worldgen.wikiful"
version = "0.3.2"

cloche {
    metadata {
        modId = "wikiful"
        name = "Wikiful"
        description = "In-game wiki library mod."
        license = "MIT"
        icon = "pack.png"

        author("Apollo")
    }

    common {
        mixins.from(file("src/common/main/wikiful.mixins.json"))

        dependencies {
            compileOnly("org.spongepowered:mixin:0.8.3")
        }
    }

    val sharedOld = common("shared:21.1") {
        mixins.from(file("src/shared/21.1/main/wikiful.21.1.mixins.json"))
        accessWideners.from(file("src/shared/21.1/main/wikiful.21.1.accesswidener"))
    }
    val sharedNew = common("shared:26.1") {
        mixins.from(file("src/shared/26.1/main/wikiful.26.1.mixins.json"))
        accessWideners.from(file("src/shared/26.1/main/wikiful.26.1.accesswidener"))
    }

    fabric("fabric:21.1") {
        dependsOn(sharedOld)

        loaderVersion = "0.18.4"
        minecraftVersion = "1.21.1"

        mappings {
            official()
            custom(minecraftVersion.map {
                project.dependencies.create(files("mappings/$it.tiny"))
            })
        }

        dependencies {
            fabricApi("0.116.1")
        }

        includedClient()
        runs {
            client()
            server()
        }

        metadata {
            entrypoint("main") {
                value = "dev.worldgen.wikiful.impl.WikifulEntrypoint"
            }
            entrypoint("client") {
                value = "dev.worldgen.wikiful.impl.WikifulClientEntrypoint"
            }
        }
    }

    fabric("fabric:26.1") {
        dependsOn(sharedNew)

        loaderVersion = "0.18.4"
        minecraftVersion = "26.1"

        dependencies {
            fabricApi("0.144.3")
        }

        includedClient()
        runs {
            client()
            server()
        }

        metadata {
            entrypoint("main") {
                value = "dev.worldgen.wikiful.impl.WikifulEntrypoint"
            }
            entrypoint("client") {
                value = "dev.worldgen.wikiful.impl.WikifulClientEntrypoint"
            }
        }
    }

    neoforge("neoforge:21.1") {
        dependsOn(sharedOld)

        loaderVersion = "21.1.218"
        minecraftVersion = "1.21.1"

        mappings {
            official()
            custom(minecraftVersion.map {
                project.dependencies.create(files("mappings/$it.tiny"))
            })
        }

        runs {
            client()
            server()
        }
    }

    neoforge("neoforge:26.1") {
        dependsOn(sharedNew)

        loaderVersion = "26.1.0.8-beta"
        minecraftVersion = "26.1"

        runs {
            client()
            server()
        }
    }
}