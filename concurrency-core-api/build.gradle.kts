tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

dependencies {
    implementation(project(":storage:db-core"))
    implementation(project(":storage:db-redis"))
    implementation(project(":infra:logging"))
    implementation(project(":clients:example"))
    implementation("org.springframework.boot:spring-boot-starter-web")
}