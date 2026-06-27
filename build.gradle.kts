import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.text.SimpleDateFormat
import java.util.Date

plugins {
    idea
    `maven-publish`
    id("net.neoforged.moddev.legacyforge") version "2.0.91"
    id("org.jetbrains.kotlin.jvm") version "2.2.21"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.21"
    id("me.modmuss50.mod-publish-plugin") version "2.0.1"
}

val mod_id: String by project
val mod_name: String by project
val mod_version: String by project
val mod_authors: String by project
val mod_description: String by project
val mod_credits: String by project
val mod_license: String by project
val mod_group_id: String by project
val kff_version: String by project
val kff_version_range: String by project
val minecraft_version: String by project
val minecraft_version_range: String by project
val forge_version: String by project
val forge_version_range: String by project
val mapping_version: String by project
val gtceu_version: String by project
val gtceu_version_range: String by project
val ldlib_version: String by project
val ldlib_version_range: String by project
val registrate_version: String by project
val configuration_version: String by project
val configuration_version_range: String by project
val tiab_version: String by project
val tiab_version_range: String by project
val tiab_curse_file_id: String by project
val emi_version: String by project
val jade_curse_file_id: String by project
val cf_project_id: String by project
val cf_project_slug: String by project
val modrinth_project_id: String by project
val forgejo_host: String by project
val forgejo_repository: String by project

version = mod_version
group = mod_group_id
base {
    archivesName.set(mod_id)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

legacyForge {
    version = "${minecraft_version}-${forge_version}"

    parchment {
        mappingsVersion = mapping_version
        minecraftVersion = minecraft_version
    }

    runs {
        configureEach {
            systemProperty("forge.logging.markers", "REGISTRIES")
            logLevel = org.slf4j.event.Level.DEBUG
            programArguments.addAll("--mixin", "${mod_id}.mixins.json")
        }
        create("client") {
            client()
            sourceSet = sourceSets["main"]
            systemProperty("forge.enabledGameTestNamespaces", mod_id)
            programArguments.addAll("--refresh-dependencies")
        }
        create("server") {
            server()
            sourceSet = sourceSets["main"]
            systemProperty("forge.enabledGameTestNamespaces", mod_id)
            programArguments.addAll("--nogui", "--world", "world-extra")
        }
        create("data") {
            data()
            sourceSet = sourceSets["main"]
            programArguments.addAll(
                "--mod", mod_id, "--all",
                "--output", file("src/generated/resources/").absolutePath,
                "--existing", file("src/main/resources/").absolutePath,
            )
            programArguments.addAll("--existing-mod", "gtceu")
        }
    }

    mods {
        register(mod_id) {
            sourceSet(sourceSets["main"])
        }
    }
}

sourceSets["main"].resources {
    srcDir("src/generated/resources")
}

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        name = "GTCEu Maven"
        url = uri("https://maven.gtceu.com")
    }
    maven {
        name = "FirstDarkDev"
        url = uri("https://maven.firstdark.dev/snapshots/")
    }
    maven {
        url = uri("https://maven.tterrag.com/")
        content { includeGroup("com.tterrag.registrate") }
    }
    maven {
        name = "BlameJared"
        url = uri("https://maven.blamejared.com/")
    }
    maven {
        url = uri("https://maven.theillusivec4.top/")
    }
    maven {
        url = uri("https://cursemaven.com/")
        content { includeGroup("curse.maven") }
    }
    maven {
        name = "TerraformersMC"
        url = uri("https://maven.terraformersmc.com/")
    }
    maven {
        name = "Kotlin for Forge"
        url = uri("https://thedarkcolour.github.io/KotlinForForge/")
        content { includeGroup("thedarkcolour") }
    }
    maven {
        name = "MangoRage"
        url = uri("https://maven.mangorage.org/releases")
        content { includeGroup("org.mangorage") }
    }
    maven {
        name = "SpongePowered"
        url = uri("https://repo.spongepowered.org/repository/maven-public/")
        content { includeGroup("org.spongepowered") }
    }
}

dependencies {
    compileOnly("org.jetbrains:annotations:26.0.1")
    implementation("thedarkcolour:kotlinforforge:${kff_version}")

    "modRuntimeOnly"("dev.emi:emi-forge:${emi_version}")
    "modRuntimeOnly"("curse.maven:jade-324717:${jade_curse_file_id}")

    // GregTech and dependencies
    "modImplementation"("com.gregtechceu.gtceu:gtceu-${minecraft_version}:${gtceu_version}:slim") { isTransitive = false }
    "modImplementation"("com.lowdragmc.ldlib:ldlib-forge-${minecraft_version}:${ldlib_version}") { isTransitive = false }
    "modImplementation"("com.tterrag.registrate:Registrate:${registrate_version}")
    "modImplementation"("dev.toma.configuration:configuration-forge-${minecraft_version}:${configuration_version}")

    // Time in a Bottle
    "modImplementation"("org.mangorage:tiab-common-api:${tiab_version}")
    "modRuntimeOnly"("curse.maven:time-in-a-bottle-895919:${tiab_curse_file_id}")

    annotationProcessor("org.spongepowered:mixin:0.8.5:processor")
}

tasks.named<ProcessResources>("processResources") {
    val properties = mapOf(
        "mod_id" to mod_id,
        "mod_name" to mod_name,
        "mod_version" to mod_version,
        "mod_authors" to mod_authors,
        "mod_description" to mod_description,
        "mod_credits" to mod_credits,
        "mod_license" to mod_license,
        "mod_group_id" to mod_group_id,
        "kff_version_range" to kff_version_range,
        "minecraft_version" to minecraft_version,
        "minecraft_version_range" to minecraft_version_range,
        "forge_version" to forge_version,
        "forge_version_range" to forge_version_range,
        "gtceu_version" to gtceu_version,
        "gtceu_version_range" to gtceu_version_range,
        "tiab_version_range" to tiab_version_range,
    )
    inputs.properties(properties)
    filesMatching(listOf("META-INF/mods.toml", "pack.mcmeta")) {
        expand(properties + mapOf("project" to project))
    }
}

tasks.named<Jar>("jar") {
    manifest {
        attributes(
            mapOf(
                "MixinConfigs" to "tiab_gtm_compat.mixins.json",
                "Specification-Title" to mod_id,
                "Specification-Vendor" to mod_authors,
                "Specification-Version" to "1",
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version,
                "Implementation-Vendor" to mod_authors,
                "Implementation-Timestamp" to SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(Date()),
            )
        )
    }
    finalizedBy("reobfJar")
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.jvmTarget = JvmTarget.JVM_17
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(17)
    options.compilerArgs.add("-Aquiet=true")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifact(tasks.named<Jar>("jar"))
        }
    }
    repositories {
        maven {
            url = uri("file://${project.projectDir}/mcmodsrepo")
        }
    }
}

publishMods {
    file.set(tasks.named<Jar>("jar").flatMap { it.archiveFile })
    changelog.set(file("CHANGELOG.md").readText())
    type.set(STABLE)
    modLoaders.add("forge")

    curseforge {
        projectId.set(cf_project_id)
        projectSlug.set(cf_project_slug)
        accessToken.set(providers.environmentVariable("CURSEFORGE_TOKEN"))
        minecraftVersions.add(minecraft_version)
        client.set(true)
        server.set(true)
    }

    modrinth {
        projectId.set(modrinth_project_id)
        accessToken.set(providers.environmentVariable("MODRINTH_TOKEN"))
        minecraftVersions.add(minecraft_version)
    }

    forgejo {
        accessToken.set(providers.environmentVariable("GIT_TOKEN"))
        host.set(java.net.URI(forgejo_host))
        repository.set(forgejo_repository)
        commitish.set("main")
        tagName.set(mod_version)
    }
}
