package prieto.fernando.kotlin.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import prieto.fernando.dependencies.Dependencies
import prieto.fernando.dependencies.TestDependencies

open class KotlinPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.configurePlugins()
        project.configureDependencies()
    }

    private fun Project.configurePlugins() {
        val pluginsToApply = listOf(
            "kotlin",
            "kotlin-kapt",
            "io.gitlab.arturbosch.detekt"
        )
        println("KotlinPlugin: applying plugins $pluginsToApply")
        pluginsToApply.forEach(plugins::apply)
    }

    private fun Project.configureDependencies() = dependencies {
        fun kapt(definition: Any) = "kapt"(definition)
        fun implementation(definition: Any) = "implementation"(definition)
        fun testImplementation(definition: Any) = "testImplementation"(definition)

        testImplementation(kotlin("test"))
        implementation(Dependencies.kotlinxCoroutines)
        implementation(Dependencies.timber)
        kapt(Dependencies.Dagger.daggerCompiler)
        kapt(Dependencies.Hilt.hiltCompiler)
        testImplementation(TestDependencies.kotlinxCoroutines)
        testImplementation(TestDependencies.JUnit.junit)
        testImplementation(TestDependencies.JUnit.junitPlatformRunner)
        testImplementation(TestDependencies.Mockk.mockk)
        testImplementation(TestDependencies.Mockk.mockkAgentJvm)
    }
} 
