val depInTOML: MinimalExternalModuleDependency = privateLibKotlin.common.reflect.get()
val mvnGroupID: String = requireNotNull(depInTOML.group)
val mvnArtifactID: String = depInTOML.name
val mvnVersion: String = requireNotNull(depInTOML.version)


plugins {
    alias(libKotlin.plugins.core)
    id(privateLibJava.plugins.java.version.get().pluginId)
    id(privateLibJava.plugins.publish.private.get().pluginId)
}

tasks.withType<Test> {
    // 连接Gradle测试任务与JUnit工具
    useJUnitPlatform()
}

dependencies {
    // Java反射工具
    api(privateLibJava.common.reflect)

    // Kotlin反射工具
    api(libKotlin.reflect)

    // JUnit5 BOM版本配置文件
    testImplementation(platform(libJava.junit5.bom))
    // JUnit5 平台启动器
    testImplementation(libJava.junit5.launcher)
    // Jupiter（JUnit5引擎的实现）
    testImplementation(libJava.junit5.jupiter)
}

javaVersionConfig {
    jdkVersion = JavaVersion.VERSION_1_8
}

privatePublishConfig {
    groupID = mvnGroupID
    artifactID = mvnArtifactID
    version = mvnVersion
}
