plugins {
    kotlin("jvm") version "2.1.0"
    id("earth.terrarium.cloche") version "0.18.1"
}

repositories {
    cloche {
        mavenNeoforgedMeta()
        mavenNeoforged()
        mavenForge()
        mavenFabric()
        mavenParchment()
        librariesMinecraft()
        main()
    }
    mavenCentral()
    maven("https://api.modrinth.com/maven")
}

group = "dev.worldgen.wikiful"
version = "0.2.0"

cloche {
    targets.all {
        mappings {
            official()
            custom(minecraftVersion.map {
                project.dependencies.create(files("mappings/$it.tiny"))
            })
        }
    }

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
        accessWideners.from(file("src/common/main/wikiful.accesswidener"))

    }

    fabric("fabric:1.21.11") {
        loaderVersion = "0.18.2"
        minecraftVersion = "1.21.11"

        dependencies {
            fabricApi("0.141.3")
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

    neoforge("neoforge:1.21.11") {
        loaderVersion = "21.11.38-beta"
        minecraftVersion = "1.21.11"

        runs {
            client()
            server()
        }
    }
}