package prieto.fernando.core.presentation

import io.reactivex.CompletableTransformer
import io.reactivex.FlowableTransformer
import io.reactivex.MaybeTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface SchedulerProvider {

    fun io(): Scheduler
    fun ui(): Scheduler
    fun computation(): Scheduler
    fun newThread(): Scheduler

    fun <T> doOnIoObserveOnMainObservable(): ObservableTransformer<T, T>
    fun <T> doOnIoObserveOnMainSingle(): SingleTransformer<T, T>
    fun <T> doOnIoObserveOnMainMaybe(): MaybeTransformer<T, T>
    fun <T> doOnIoObservable(): ObservableTransformer<T, T>
    fun <T> doOnIoSingle(): SingleTransformer<T, T>
    fun <T> doOnIoMaybe(): MaybeTransformer<T, T>
    fun <T> doOnIoSingleOnMainObservable(): SingleTransformer<T, T>
    fun <T> doOnComputationObserveOnMainSingle(): SingleTransformer<T, T>
    fun <T> doOnIoObserveOnMainFlowable(): FlowableTransformer<T, T>
    fun doOnIoCompletable(): CompletableTransformer
    fun doOnComputationCompletable(): CompletableTransformer
    fun doOnIoObserveOnMainCompletable(): CompletableTransformer
}

abstract class BaseSchedulerProvider :
    SchedulerProvider {

    override fun <T> doOnIoObserveOnMainObservable(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(io())
                .unsubscribeOn(io())
                .observeOn(ui())
        }
    }

    override fun <T> doOnIoObserveOnMainSingle(): SingleTransformer<T, T> {
        return SingleTransformer {
            it.subscribeOn(io())
                .unsubscribeOn(io())
                .observeOn(ui())
        }
    }

    override fun <T> doOnIoObserveOnMainMaybe(): MaybeTransformer<T, T> {
        return MaybeTransformer {
            it.subscribeOn(io())
                .unsubscribeOn(io())
                .observeOn(ui())
        }
    }

    override fun <T> doOnIoObservable(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(io())
                .unsubscribeOn(io())
        }
    }

    override fun <T> doOnIoSingle(): SingleTransformer<T, T> {
        return SingleTransformer {
            it.subscribeOn(io())
                .unsubscribeOn(io())
        }
    }

    override fun <T> doOnIoMaybe(): MaybeTransformer<T, T> {
        return MaybeTransformer {
            it.subscribeOn(io())
                .unsubscribeOn(io())
        }
    }

    override fun <T> doOnIoSingleOnMainObservable(): SingleTransformer<T, T> {
        return SingleTransformer {
            it.subscribeOn(io())
                .unsubscribeOn(io())
                .observeOn(ui())
        }
    }

    override fun doOnIoCompletable(): CompletableTransformer {
        return CompletableTransformer {
            it.subscribeOn(io())
                .unsubscribeOn(io())
        }
    }

    override fun doOnComputationCompletable(): CompletableTransformer {
        return CompletableTransformer {
            it.subscribeOn(computation())
                .unsubscribeOn(computation())
        }
    }

    override fun <T> doOnComputationObserveOnMainSingle(): SingleTransformer<T, T> {
        return SingleTransformer {
            it.subscribeOn(computation())
                .unsubscribeOn(computation())
                .observeOn(ui())
        }
    }

    override fun doOnIoObserveOnMainCompletable(): CompletableTransformer {
        return CompletableTransformer {
            it.subscribeOn(io())
                .unsubscribeOn(io())
                .observeOn(ui())
        }
    }

    override fun <T> doOnIoObserveOnMainFlowable(): FlowableTransformer<T, T> {
        return FlowableTransformer {
            it.subscribeOn(io())
                .unsubscribeOn(io())
                .observeOn(ui())
        }
    }
}

class AppSchedulerProvider : BaseSchedulerProvider() {
    override fun io(): Scheduler = Schedulers.io()
    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
    override fun computation() = Schedulers.computation()
    override fun newThread() = Schedulers.newThread()
}
