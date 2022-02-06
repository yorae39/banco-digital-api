import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.10"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    kotlin("plugin.jpa") version "1.6.10"

}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-validation
    implementation("org.springframework.boot:spring-boot-starter-validation:2.6.3")
    // https://mvnrepository.com/artifact/org.json/json
    implementation("org.json:json:20211205")
    // https://mvnrepository.com/artifact/com.google.zxing/core
    implementation("com.google.zxing:core:3.4.1")
    // https://mvnrepository.com/artifact/com.google.zxing/javase
    implementation("com.google.zxing:javase:3.4.1")
    // https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-ui
    implementation("org.springdoc:springdoc-openapi-ui:1.6.5")
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-configuration-processor
    implementation("org.springframework.boot:spring-boot-configuration-processor:2.6.3")
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-devtools
    implementation("org.springframework.boot:spring-boot-devtools:2.6.3")
    // https://mvnrepository.com/artifact/org.flywaydb/flyway-core
    implementation("org.flywaydb:flyway-core:7.1.1")
    // https://mvnrepository.com/artifact/org.flywaydb/flyway-maven-plugin
    implementation("org.flywaydb:flyway-maven-plugin:7.1.1")
    // https://mvnrepository.com/artifact/io.springfox/springfox-swagger2
    //VERIRIFICAR SE VAI DAR ERRO ENTÃO MIGRAR PARA 2.9.2
    implementation("io.springfox:springfox-swagger2:3.0.0")
    // https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui
    implementation("io.springfox:springfox-swagger-ui:3.0.0")
    // https://mvnrepository.com/artifact/io.swagger/swagger-jaxrs
    implementation("io.swagger:swagger-jaxrs:1.6.4")
    // https://mvnrepository.com/artifact/org.hibernate/hibernate-java8
    implementation("org.hibernate:hibernate-java8:5.6.5.Final")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.datatype/jackson-datatype-jsr310
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.1")
    // https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security
    implementation("org.springframework.boot:spring-boot-starter-security:2.6.3")
    // https://mvnrepository.com/artifact/org.springframework.security/spring-security-test
    testImplementation("org.springframework.security:spring-security-test:5.6.1")
    // https://mvnrepository.com/artifact/mysql/mysql-connector-java
    implementation("mysql:mysql-connector-java:8.0.28")
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-maven-plugin
    implementation("org.springframework.boot:spring-boot-maven-plugin:2.6.3")
    // https://mvnrepository.com/artifact/org.flywaydb/flyway-maven-plugin
    implementation("org.flywaydb:flyway-maven-plugin:8.4.4")
    //developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
