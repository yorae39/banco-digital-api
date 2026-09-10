// import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "4.1.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.3.20"
    kotlin("jvm") version "2.3.20"
    kotlin("plugin.spring") version "2.3.20"
    kotlin("plugin.jpa") version "2.3.20"
    kotlin("plugin.lombok") version "2.3.20"

}

group = "com.example"
version = "0.0.1-SNAPSHOT"
// java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    // https://mvnrepository.com/artifact/org.json/json
    implementation("org.json:json:20211205")
    // https://mvnrepository.com/artifact/com.google.zxing/core
    implementation("com.google.zxing:core:3.4.1")
    // https://mvnrepository.com/artifact/com.google.zxing/javase
    implementation("com.google.zxing:javase:3.4.1")
    //flyway
    implementation("org.springframework.boot:spring-boot-starter-flyway")
    implementation("org.flywaydb:flyway-mysql")
    //implementation("io.springfox:springfox-bean-validators:2.9.2")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5")
    // Kotlin DSL
    implementation("jakarta.servlet:jakarta.servlet-api:6.0.0")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.datatype/jackson-datatype-jsr310
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    // token
    implementation("io.jsonwebtoken:jjwt-api:0.12.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.5")
    // https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt
    //implementation("io.jsonwebtoken:jjwt:0.9.1")
    //validator - processor - security
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter-security")
    // Source: https://mvnrepository.com/artifact/mysql/mysql-connector-java
    runtimeOnly("com.mysql:mysql-connector-j")
    // https://mvnrepository.com/artifact/net.sourceforge.barbecue/barbecue
    implementation("net.sourceforge.barbecue:barbecue:1.5-beta1")
    // https://mvnrepository.com/artifact/org.graylog2/gelfj
    implementation("org.graylog2:gelfj:1.1.16")
    // https://mvnrepository.com/artifact/org.projectlombok/lombok
    compileOnly("org.projectlombok:lombok:1.18.22")
    // Remove a versão fixa para usar o gerenciamento do Spring Boot
    developmentOnly("org.springframework.boot:spring-boot-devtools")


    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(kotlin("test"))
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.compilerOptions {
    freeCompilerArgs.set(listOf("-Xannotation-default-target=param-property"))
}