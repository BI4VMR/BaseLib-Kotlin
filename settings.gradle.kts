@file:Suppress("UnstableApiUsage")

// 构建工具的依赖配置
pluginManagement {
    // 声明 Gradle 插件仓库
    repositories {
        // 阿里云仓库镜像： Gradle 社区插件
        maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin/") }
        // 阿里云仓库镜像： Maven 中心仓库 + JCenter
        maven { setUrl("https://maven.aliyun.com/repository/public/") }
        // 阿里云仓库镜像： Google 仓库
        maven {
            setUrl("https://maven.aliyun.com/reposiory/google/")
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        // 腾讯云仓库镜像： Maven 中心仓库 + Google + JCenter
        maven { setUrl("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/") }

        gradlePluginPortal()
        mavenCentral()
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
    }
}

// 所有模块的依赖配置
dependencyResolutionManagement {
    // 版本管理配置
    versionCatalogs {
        // 公共组件 (Java)
        create("libJava") {
            from(files("misc/version/dependency_public_java.toml"))
        }

        // 公共组件 (Kotlin)
        create("libKotlin") {
            from(files("misc/version/dependency_public_kotlin.toml"))
        }

        // 私有组件 (Java)
        create("privateLibJava") {
            from(files("misc/version/dependency_private_java.toml"))
        }

        // 私有组件 (Kotlin)
        create("privateLibKotlin") {
            from(files("misc/version/dependency_private_kotlin.toml"))
        }

        // Android 版本配置
        create("agp") {
            from(files("misc/version/agp.toml"))
        }
    }
}


/* ----- 工程结构声明 ----- */
// 主工程名称
rootProject.name = "BaseLib-Kotlin"
// 加载自定义插件
includeBuild("plugin")

// ----- 工具库 -----
// 公共组件
include(":lib_common:base")
include(":lib_common:reflect")

// 金融工具
include(":lib_finance:base")

// 媒体工具
include(":lib_media:exif")

// 外部工具
include(":lib_external:adb_core")
include(":lib_external:adb_ktx")
