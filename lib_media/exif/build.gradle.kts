val depInTOML: MinimalExternalModuleDependency = privateLibKotlin.media.exif.get()
val mvnGroupID: String = requireNotNull(depInTOML.group)
val mvnArtifactID: String = depInTOML.name
val mvnVersion: String = requireNotNull(depInTOML.version)


plugins {
    alias(libKotlin.plugins.kotlin.multiplatform)
    // alias(libKotlin.plugins.kotlin.multiplatform.android)
    alias(libKotlin.plugins.dokka)

    alias(privateLibJava.plugins.java.version)
    alias(privateLibJava.plugins.publish.private.kmp)
}


kotlin {
    /* 桌面平台配置 */
    jvm()

    /* Android 平台配置 */
    // android {
    //     namespace = "net.bi4vmr.tool.kotlin.media.exif"
    //     compileSdk = 36
    //     minSdk = 26
    //
    //     compilerOptions {
    //         jvmTarget = JvmTarget.JVM_1_8
    //     }
    //
    //     androidResources {
    //         enable = true
    //     }
    //
    //     withHostTest {
    //         isIncludeAndroidResources = true
    //     }
    // }

    /* 各平台依赖配置 */
    sourceSets {
        commonMain.dependencies {
            api(libKotlin.standardlib)
            api(libKotlin.ktx.io.core)
        }
        jvmMain.dependencies {
            api(libJava.slf4j.api)

            api(privateLibJava.common.base)
        }
        // androidMain.dependencies {
        // }

        jvmTest.dependencies {
            // JUnit5 BOM 版本配置文件
            implementation(dependencies.platform(libJava.junit5.bom))
            // JUnit5 平台启动器
            implementation(libJava.junit5.launcher)
            // Jupiter（JUnit5 引擎的实现）
            implementation(libJava.junit5.jupiter)
        }
    }
}

javaVersionConfig {
    jdkVersion = JavaVersion.VERSION_1_8
}

privatePublishConfig {
    groupID = mvnGroupID
    artifactID = mvnArtifactID
    version = mvnVersion
}
