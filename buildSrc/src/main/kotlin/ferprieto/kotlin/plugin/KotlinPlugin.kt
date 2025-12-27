package ferprieto.kotlin.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.KaptGenerateStubs
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

open class KotlinPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.configurePlugins()
        project.configureJavaCompatibility()
    }

    private fun Project.configurePlugins() {
        val pluginsToApply = listOf(
            "kotlin",
            "kotlin-kapt",
            "io.gitlab.arturbosch.detekt"
        )
        println("KotlinPlugin: applying plugins $pluginsToApply")
        pluginsToApply.forEach(plugins::apply)

        project.extensions.findByName("android")?.let {
            it.javaClass.getMethod("setNamespace", String::class.java).invoke(it, "ferprieto.kotlin")
        }

        tasks.withType(KotlinCompile::class.java) {
            kotlinOptions.jvmTarget = "17"
        }
    }

    private fun Project.configureJavaCompatibility() {
        tasks.withType(org.gradle.api.tasks.compile.JavaCompile::class.java).configureEach {
            sourceCompatibility = "17"
            targetCompatibility = "17"
        }
        tasks.withType(KotlinCompile::class.java) {
            kotlinOptions.jvmTarget = "17"
        }
        tasks.withType(KaptGenerateStubs::class.java) {
            kotlinOptions.jvmTarget = "17"
        }
    }


} 
