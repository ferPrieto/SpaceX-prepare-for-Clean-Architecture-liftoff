package prieto.fernando.core.event

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event<out T> internal constructor(private val content: T) {

    @Suppress("MemberVisibilityCanBePrivate")
    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun handleContent(): T? {
        if (hasBeenHandled) return null

        hasBeenHandled = true
        return content
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}

fun <T> eventOf(value: T) = Event(value)

inline fun <T> Event<T>.handleOrPass(block: (T) -> Unit) {
    handleContent()?.apply(block)
}
