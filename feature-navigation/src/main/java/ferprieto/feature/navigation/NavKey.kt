package ferprieto.feature.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavKey {
    @Serializable
    data object Dashboard : NavKey

    @Serializable
    data object Launches : NavKey
}

