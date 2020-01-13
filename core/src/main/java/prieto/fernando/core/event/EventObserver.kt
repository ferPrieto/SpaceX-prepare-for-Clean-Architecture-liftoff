package prieto.fernando.core.event

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

class EventObserver<E : Event<T>, T>(private val block: (T) -> Unit) : Observer<E> {
    override fun onChanged(t: E) = t.handleOrPass(block)
}

@MainThread
inline fun <E : Event<T>, T> LiveData<E>.observeEvent(
    owner: LifecycleOwner,
    crossinline onChanged: (T) -> Unit
): Observer<E> {
    val wrappedObserver: Observer<E> = EventObserver { t -> onChanged.invoke(t) }
    observe(owner, wrappedObserver)
    return wrappedObserver
}

@MainThread
inline fun <E : Event<Unit>> LiveData<E>.observeUnitEvent(
    owner: LifecycleOwner,
    crossinline onChanged: () -> Unit
): Observer<E> {
    val wrappedObserver: Observer<E> = EventObserver { onChanged.invoke() }
    observe(owner, wrappedObserver)
    return wrappedObserver
}
