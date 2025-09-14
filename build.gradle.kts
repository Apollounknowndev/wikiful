plugins {
    kotlin("jvm") version "2.1.0"
    id("earth.terrarium.cloche") version "0.13.2"
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
    mappings {
        official()
        parchment("2025.07.20@zip")
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

    fabric("fabric:1.21.8") {
        loaderVersion = "0.17.0"
        minecraftVersion = "1.21.8"

        dependencies {
            fabricApi("0.131.0")
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

    neoforge("neoforge:1.21.8") {
        loaderVersion = "21.8.4-beta"
        minecraftVersion = "1.21.8"

        runs {
            client()
            server()
        }
    }
}