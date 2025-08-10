@file:Suppress("UnstableApiUsage")

// 构建工具的依赖配置
pluginManagement {
    // 声明Gradle插件仓库
    repositories {
        // 腾讯云仓库镜像：Maven中心仓库+Spring+Google+JCenter
        maven { setUrl("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/") }
        // 阿里云仓库镜像：Gradle社区插件
        maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin/") }
        // 阿里云仓库镜像：Maven中心仓库+JCenter
        maven { setUrl("https://maven.aliyun.com/repository/public/") }
        // 阿里云仓库镜像：Google仓库
        maven { setUrl("https://maven.aliyun.com/repository/google/") }

        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

// 所有模块的依赖配置
dependencyResolutionManagement {
    // 版本管理配置
    versionCatalogs {
        // 公共组件(Java)
        create("libJava") {
            from(files("misc/version/dependency_public_java.toml"))
        }

        // 公共组件(Kotlin)
        create("libKotlin") {
            from(files("misc/version/dependency_public_kotlin.toml"))
        }

        // 私有组件(Java)
        create("privateLibJava") {
            from(files("misc/version/dependency_private_java.toml"))
        }

        // 私有组件(Kotlin)
        create("privateLibKotlin") {
            from(files("misc/version/dependency_private_kotlin.toml"))
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

// 依赖传递
include(":lib_all")
