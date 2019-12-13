package prieto.fernando.core.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import dagger.android.support.DaggerFragment
import prieto.fernando.core.presentation.BaseViewModel
import prieto.fernando.core.presentation.ViewModelProviderFactory
import javax.inject.Inject

abstract class BaseFragment<T : BaseViewModel> : DaggerFragment(), BaseView<T> {

    @Inject
    protected lateinit var vmFactory: ViewModelProviderFactory<T>

    protected fun <T : ViewModel> getViewModel(viewModelClass: Class<T>) =
        ViewModelProviders.of(this, vmFactory).get(viewModelClass)
}
