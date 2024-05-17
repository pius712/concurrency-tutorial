dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    api("org.redisson:redisson-spring-boot-starter:3.30.0")

    compileOnly("org.springframework.boot:spring-boot-starter-web")
}
