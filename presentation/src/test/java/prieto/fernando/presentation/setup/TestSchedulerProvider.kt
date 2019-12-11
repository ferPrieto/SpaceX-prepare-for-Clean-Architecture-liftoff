package prieto.fernando.presentation.setup

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import prieto.fernando.core.presentation.BaseSchedulerProvider

class TestSchedulerProvider : BaseSchedulerProvider() {
    override fun io(): Scheduler = Schedulers.trampoline()
    override fun ui(): Scheduler = Schedulers.trampoline()
    override fun computation(): Scheduler = Schedulers.trampoline()
    override fun newThread(): Scheduler = Schedulers.trampoline()
}
