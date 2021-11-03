package prieto.fernando.android.plugin

import AndroidSettings
import Dependencies
import TestDependencies
import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

open class AndroidPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        @Suppress("UnstableApiUsage")
        val extension = project.extensions.create<AndroidPluginExtension>("androidPlugin")

        project.configurePlugins(extension.buildType)
        project.configureAndroid()
        project.configureDependencies()

        project.afterEvaluate {
            with(project) {
                tasks {
                    withType<KotlinCompile> {
                        with(kotlinOptions) {
                            jvmTarget = "1.8"
                            freeCompilerArgs = listOf("-Xallow-result-return-type")
                            languageVersion = "1.5"
                            useIR = true
                        }
                    }
                }
            }
        }
    }

    private fun androidPlugins() = listOf(
        "kotlin-android",
        "kotlin-android-extensions"
    )

    private fun Project.configurePlugins(buildType: BuildType) = listOf(
        when (buildType) {
            BuildType.AndroidLibrary, BuildType.App -> androidPlugins()
            BuildType.Library -> listOf("kotlin")
        },
        listOf("kotlin-kapt")
    ).flatten()
        .also { println("AndroidPlugin: applying plugins $it") }
        .forEach(plugins::apply)

    private fun Project.configureAndroid() = extensions.getByType(BaseExtension::class.java).run {
        compileSdkVersion(AndroidSettings.compileSdk)
        buildToolsVersion(AndroidSettings.buildTools)

        defaultConfig {
            versionCode = 1
            versionName = "1.0"

            testInstrumentationRunner = AndroidSettings.testInstrumentationRunner

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
                        "META-INF/LGPL2.1"
                    )
                )
            }

            buildTypes {
                getByName("debug") {
                    isDebuggable = true
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
            unitTests.isReturnDefaultValues = true
            animationsDisabled = true
        }

        lintOptions {
            isAbortOnError = false
        }
    }

    private fun Project.configureDependencies() = dependencies {
        fun kapt(definition: Any) = "kapt"(definition)
        fun implementation(definition: Any) = "implementation"(definition)
        fun testImplementation(definition: Any) = "testImplementation"(definition)
        fun androidTestImplementation(definition: Any) = "androidTestImplementation"(definition)

        implementation(kotlin("stdlib-jdk7"))
        testImplementation(kotlin("test"))

        implementation(Dependencies.kotlinxCoroutines)

        implementation(Dependencies.timber)

        kapt(Dependencies.Dagger.daggerCompiler)
        kapt(Dependencies.Dagger.daggerAndroidProcessor)

        androidTestImplementation(TestDependencies.AndroidX.core)
        androidTestImplementation(TestDependencies.AndroidX.coreKtx)
        androidTestImplementation(TestDependencies.AndroidX.runner)
        androidTestImplementation(TestDependencies.AndroidX.rules)
        androidTestImplementation(TestDependencies.AndroidX.espressoCore)
        androidTestImplementation(TestDependencies.AndroidX.espressoContrib)
        androidTestImplementation(TestDependencies.AndroidX.junit)
        testImplementation(TestDependencies.livedataTesting)

        testImplementation(TestDependencies.kotlinxCoroutines)
        androidTestImplementation(TestDependencies.kotlinxCoroutines)

        testImplementation(TestDependencies.JUnit.junit)
        testImplementation(TestDependencies.JUnit.junitPlatformRunner)

        testImplementation(TestDependencies.Mockito.mockitoCore)
        testImplementation(TestDependencies.Mockito.mockitoInline)
        testImplementation(TestDependencies.Mockito.mockitoKotlin)
        testImplementation(TestDependencies.AndroidX.coreTesting)
        testImplementation(Dependencies.jodaTime)
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

fun androidPluginId() = "prieto.fernando.android.plugin"
