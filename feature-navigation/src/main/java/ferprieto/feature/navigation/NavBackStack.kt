package ferprieto.feature.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Stable
class NavBackStack(
    initialBackStack: List<NavKey> = listOf(NavKey.Dashboard)
) {
    private val _backStack = mutableStateListOf<NavKey>().apply {
        addAll(initialBackStack)
    }

    val backStack: List<NavKey> get() = _backStack

    val current: NavKey?
        get() = _backStack.lastOrNull()

    fun push(key: NavKey) {
        if (_backStack.lastOrNull() != key) {
            _backStack.add(key)
        }
    }

    fun pop(): Boolean {
        return if (_backStack.size > 1) {
            _backStack.removeLast()
            true
        } else {
            false
        }
    }

    fun popTo(key: NavKey, inclusive: Boolean = false): Boolean {
        val index = _backStack.lastIndexOf(key)
        if (index == -1) return false

        val targetIndex = if (inclusive) index else index + 1
        if (targetIndex < _backStack.size) {
            val itemsToRemove = _backStack.size - targetIndex
            repeat(itemsToRemove) {
                _backStack.removeLast()
            }
            return true
        }
        return false
    }

    fun replace(key: NavKey) {
        if (_backStack.isNotEmpty()) {
            _backStack.removeLast()
        }
        _backStack.add(key)
    }

    companion object {
        fun saver() = Saver<NavBackStack, String>(
            save = { backStack ->
                Json.encodeToString(backStack.backStack)
            },
            restore = { json ->
                val keys = Json.decodeFromString<List<NavKey>>(json)
                NavBackStack(keys)
            }
        )
    }
}

@Composable
fun rememberNavBackStack(
    initialBackStack: List<NavKey> = listOf(NavKey.Dashboard)
): NavBackStack {
    return rememberSaveable(saver = NavBackStack.saver()) {
        NavBackStack(initialBackStack)
    }
}

