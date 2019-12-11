package prieto.fernando.presentation.setup

import prieto.fernando.core.presentation.BaseViewModel

fun setupViewModelForTests(baseViewModel: BaseViewModel) {
    baseViewModel.schedulerProvider = TestSchedulerProvider()
}
