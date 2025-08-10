val mvnGroupID: String = "net.bi4vmr.tool.kotlin"
val mvnArtifactID: String = "all"
val mvnVersion: String = "1.0.0"


plugins {
    id(libJava.plugins.java.library.get().pluginId)
    id(libJava.plugins.maven.publish.get().pluginId)
}

dependencies {
    implementation(privateLibKotlin.common.base)
}

publishing {
    repositories {
        // 私有仓库
        maven {
            name = "private"
            isAllowInsecureProtocol = true
            setUrl("http://172.16.5.1:8081/repository/maven-private/")
            credentials {
                username = "uploader"
                password = "uploader"
            }
        }
    }

    publications {
        // 创建名为"maven"的发布配置
        create<MavenPublication>("maven") {
            // 产物的基本信息
            groupId = mvnGroupID
            artifactId = mvnArtifactID
            version = mvnVersion

            // POM信息
            pom {
                // 打包格式
                packaging = "pom"

                name.set(mvnArtifactID)
                url.set("https://github.com/BI4VMR/BaseLib-Java")
                developers {
                    developer {
                        name.set("BI4VMR")
                        email.set("bi4vmr@outlook.com")
                    }
                }
            }

            pom.withXml {
                val dependenciesNode = asNode().appendNode("dependencies")
                // 获取当前模块的所有"implementation"节点
                configurations.getByName("implementation").allDependencies.forEach { dependency ->
                    val group: String? = dependency.group
                    val artifact: String = dependency.name
                    val version: String? = dependency.version
                    println("Parse dependency item. Group:[$group] Artifact:[$artifact] Version:[$version]")

                    // 向POM中添加依赖项，并跳过组名与版本号为空的项。
                    if (group != null && version != null) {
                        val dependencyNode = dependenciesNode.appendNode("dependency")
                        dependencyNode.appendNode("groupId", group)
                        dependencyNode.appendNode("artifactId", artifact)
                        dependencyNode.appendNode("version", version)
                        dependencyNode.appendNode("scope", "compile")
                    }
                }
            }
        }
    }
}
