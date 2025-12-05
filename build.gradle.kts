plugins {
	java
	id("org.springframework.boot") version "4.0.0"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.alysrazor"
version = "0.0.1-SNAPSHOT"
description = "Proyecto en Spring boot."

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-batch:4.0.0")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:4.0.0")
	implementation("org.springframework.boot:spring-boot-starter-security:4.0.0")
	implementation("org.springframework.boot:spring-boot-starter-web:4.0.0")
    implementation("com.mysql:mysql-connector-j:8.4.0")
    implementation("org.mapstruct:mapstruct:1.6.3")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
	compileOnly("org.projectlombok:lombok:1.18.42")
	annotationProcessor("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

	testImplementation("org.springframework.boot:spring-boot-starter-test:4.0.0")
	testImplementation("org.springframework.batch:spring-batch-test:4.0.0")
	testImplementation("org.springframework.security:spring-security-test:4.0.0")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher:6.0.1")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
