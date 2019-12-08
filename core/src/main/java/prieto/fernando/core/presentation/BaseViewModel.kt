package prieto.fernando.core.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

interface BaseViewModelInputs

interface BaseViewModelOutputs {
    fun error(): Observable<Int>
    fun finish(): Observable<Unit>
    fun refreshing(): Observable<Boolean>
}

open class BaseViewModel() : ViewModel(),
    BaseViewModelInputs,
    BaseViewModelOutputs {

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    protected val subscriptions = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }

    open val inputs: BaseViewModelInputs
        get() = this

    open val outputs: BaseViewModelOutputs
        get() = this

    protected val finish: Subject<Unit> = PublishSubject.create()
    protected val error: Subject<Int> = PublishSubject.create()
    protected val refreshing: Subject<Boolean> = BehaviorSubject.createDefault(false)

    override fun error(): Observable<Int> {
        return error.observeOn(schedulerProvider.ui())
            .hide()
    }

    override fun finish(): Observable<Unit> {
        return finish.observeOn(schedulerProvider.ui())
            .hide()
    }

    override fun refreshing(): Observable<Boolean> {
        return refreshing.observeOn(schedulerProvider.ui())
            .hide()
    }
}
