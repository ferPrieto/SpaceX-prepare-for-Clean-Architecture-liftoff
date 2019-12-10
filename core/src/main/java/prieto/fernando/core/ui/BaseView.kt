package prieto.fernando.core.ui

import androidx.lifecycle.ViewModel

interface BaseView<T : ViewModel> {
    val viewModel: T
}
