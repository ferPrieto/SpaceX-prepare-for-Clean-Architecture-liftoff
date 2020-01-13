package prieto.fernando.presentation.setup

fun setupViewModelForTests(baseViewModel: BaseViewModel) {
    baseViewModel.schedulerProvider = TestSchedulerProvider()
}
