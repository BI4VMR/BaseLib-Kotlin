package net.bi4vmr.gradle.plugin

import net.bi4vmr.gradle.data.Plugins
import net.bi4vmr.gradle.util.NetUtil
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.javadoc.Javadoc
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.register

/**
 * 私有Maven发布插件。
 *
 * @author BI4VMR@outlook.com
 * @since 1.0.0
 */
class PrivatePublishPlugin : Plugin<Project> {

    companion object {

        const val NAME: String = "net.bi4vmr.gradle.plugin.maven.publish"

        // 全局保存首次网络测试结果，避免每个子模块应用本插件都测试网络导致速度缓慢。
        private var privateRepoAvailable: Boolean? = null
        private var localRepoAvailable: Boolean? = null
    }

    override fun apply(target: Project) {
        // 检查仓库是否可用
        if (privateRepoAvailable == null) {
            privateRepoAvailable = NetUtil.scanByTCP("172.16.5.1", 8081)
        }
        if (localRepoAvailable == null) {
            localRepoAvailable = NetUtil.scanByTCP("127.0.0.1", 8081)
        }

        // 注册扩展
        target.extensions.create(PrivatePublishConfig.NAME, PrivatePublishConfig::class.java)
        // 应用Maven Publish插件
        target.pluginManager.apply(Plugins.MAVEN_PUBLISH)

        target.plugins.withId(Plugins.MAVEN_PUBLISH) {
            target.afterEvaluate {
                val ext = target.extensions.findByType(PrivatePublishConfig::class.java)
                    ?: throw IllegalArgumentException("Please use `privatePublishConfig {}` to register maven group and name info!")

                // 检查是否设置了必填属性
                ext.groupID
                    ?: throw IllegalArgumentException("Please set 'groupID' in `privatePublishConfig {}`!")
                ext.artifactID
                    ?: throw IllegalArgumentException("Please set 'artifactID' in `privatePublishConfig {}`!")

                target.extensions.configure<PublishingExtension> {
                    repositories {
                        // 私有仓库
                        if (privateRepoAvailable == true) {
                            maven {
                                name = "Private"
                                isAllowInsecureProtocol = true
                                setUrl("http://172.16.5.1:8081/repository/maven-private/")
                                credentials {
                                    username = "uploader"
                                    password = "uploader"
                                }
                            }
                        }

                        // 本机仓库
                        if (localRepoAvailable == true) {
                            maven {
                                name = "Local"
                                isAllowInsecureProtocol = true
                                setUrl("http://127.0.0.1:8081/repository/maven-private/")
                                credentials {
                                    username = "uploader"
                                    password = "uploader"
                                }
                            }
                        }
                    }

                    publications {
                        // 创建名为"Maven"的发布配置
                        register<MavenPublication>("Maven") {
                            // 产物的基本信息
                            groupId = ext.groupID
                            artifactId = ext.artifactID
                            version = ext.version

                            // 发布程序包
                            from(target.components.getByName("java"))

                            val projectName: String = target.rootProject.name

                            // POM信息
                            pom {
                                // 打包格式
                                packaging = "jar"
                                name.set(ext.artifactID)
                                url.set("https://github.com/BI4VMR/$projectName")
                                developers {
                                    developer {
                                        name.set("BI4VMR")
                                        email.set("bi4vmr@outlook.com")
                                    }
                                }
                            }
                        }
                    }
                }

                target.extensions.configure<JavaPluginExtension> {
                    if (ext.uploadSources) {
                        withSourcesJar()
                    }

                    if (ext.uploadJavadoc) {
                        withJavadocJar()

                        // 指定JavaDoc编码，解决系统编码与文件不一致导致错误。
                        target.tasks.withType(Javadoc::class.java).configureEach {
                            options.encoding = "UTF-8"
                        }
                    }
                }
            }
        }
    }
}
