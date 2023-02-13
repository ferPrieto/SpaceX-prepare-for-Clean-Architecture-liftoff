package prieto.fernando.android.plugin

import prieto.fernando.dependencies.AndroidSettings
import prieto.fernando.dependencies.Dependencies
import prieto.fernando.dependencies.TestDependencies
import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.*
import prieto.fernando.dependencies.AndroidSettings.minSdk

open class AndroidPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        @Suppress("UnstableApiUsage")
        val extension = project.extensions.create<AndroidPluginExtension>("androidPlugin")

        project.configurePlugins(extension.buildType)
        project.configureAndroid()
        project.configureDependencies()
    }

    private fun androidPlugins() = listOf(
        "kotlin-android"
    )

    private fun Project.configurePlugins(buildType: BuildType) = listOf(
        when (buildType) {
            BuildType.AndroidLibrary, BuildType.App -> androidPlugins()
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
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
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
            unitTests.isReturnDefaultValues = true
            animationsDisabled = true
        }

        tasks.named("check").configure {
            this.setDependsOn(
                this.dependsOn.filterNot {
                    it is TaskProvider<*> && it.name == "detekt"
                }
            )
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

        kapt(Dependencies.Hilt.hiltAndroid)
        kapt(Dependencies.Hilt.hiltAndroidCompiler)

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

        testImplementation(TestDependencies.Mockk.mockk)
        testImplementation(TestDependencies.Mockk.mockkAgentJvm)
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
