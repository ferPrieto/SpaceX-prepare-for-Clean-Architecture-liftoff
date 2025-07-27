package prieto.fernando.android.plugin

import prieto.fernando.dependencies.AndroidSettings
import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.*
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.jetbrains.kotlin.gradle.tasks.KaptGenerateStubs
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion

open class AndroidPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        @Suppress("UnstableApiUsage")
        val extension = project.extensions.create<AndroidPluginExtension>("androidPlugin")

        // Auto-detect build type based on project name
        val buildType = when {
            project.name == "app" -> BuildType.App
            project.name == "data-api" -> BuildType.AndroidLibrary
            else -> extension.buildType
        }
        
                    project.configurePlugins(buildType)
            project.configureAndroid()
    }

    private fun androidPlugins() = listOf(
        "kotlin-android"
    )

    private fun Project.configurePlugins(buildType: BuildType) = listOf(
        when (buildType) {
            BuildType.AndroidLibrary -> listOf("com.android.library") + androidPlugins()
            BuildType.App -> listOf("com.android.application") + androidPlugins()
            BuildType.Library -> listOf("kotlin")
        },
        listOf("kotlin-kapt"),
        listOf("io.gitlab.arturbosch.detekt")
    ).flatten()
        .also { println("AndroidPlugin: applying plugins $it") }
        .forEach(plugins::apply)

    private fun Project.configureAndroid() = extensions.getByType(BaseExtension::class.java).run {
        compileSdkVersion(AndroidSettings.compileSdk)
        buildToolsVersion(AndroidSettings.buildTools)
        
        // Configure JVM toolchain for consistent JDK usage
        project.extensions.findByType(JavaPluginExtension::class.java)?.let {
            it.toolchain {
                languageVersion.set(JavaLanguageVersion.of(17))
            }
        }

        defaultConfig {
            versionCode = 1
            versionName = "1.0"
            minSdk = AndroidSettings.minSdk

            testInstrumentationRunner = "prieto.fernando.spacex.webmock.MockTestRunner"

            packagingOptions {
                resources.excludes.addAll(
                    listOf(
                        "META-INF/DEPENDENCIES",
                        "META-INF/LICENSE",
                        "META-INF/LICENSE.txt",
                        "META-INF/license.txt",
                        "META-INF/NOTICE",
                        "META-INF/NOTICE.txt",
                        "META-INF/notice.txt",
                        "META-INF/ASL2.0",
                        "META-INF/*.kotlin_module",
                        "META-INF/AL2.0",
                        "META-INF/LGPL2.1",
                        "META-INF/gradle/incremental.annotation.processors"
                    )
                )
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }

            // Configure Kotlin compilation options
            tasks.withType(KotlinCompile::class.java) {
                kotlinOptions {
                    jvmTarget = "17"
                }
            }

            buildTypes {
                getByName("debug") {
                    isDebuggable = true
                    // AGP 7.3+
                    enableAndroidTestCoverage = true
                    enableUnitTestCoverage = true
                    // AGP before 7.3
                    isTestCoverageEnabled = true
                    buildConfigField("Integer", "PORT", "8080")
                    
                }
                getByName("release") {
                    isMinifyEnabled = false
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        file("proguard-rules.pro")
                    )
                }
            }
        } 

        testOptions {
            animationsDisabled = true
            unitTests.isIncludeAndroidResources = true
            unitTests.all {
                it.extensions.configure<JacocoTaskExtension> {
                    isEnabled = true
                }
            }
        }

        tasks.named("check").configure {
            this.setDependsOn(
                this.dependsOn.filterNot {
                    it is TaskProvider<*> && it.name == "detekt"
                }
            )
        }

        // Configure kapt tasks separately to ensure JVM target consistency
        project.tasks.withType(KaptGenerateStubs::class.java) {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }


}

open class AndroidPluginExtension {
    var buildType = BuildType.AndroidLibrary
}

enum class BuildType {
    Library,
    AndroidLibrary,
    App
} 