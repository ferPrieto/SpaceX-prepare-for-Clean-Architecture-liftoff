package prieto.fernando.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class ViewModelProviderFactory<VMType : ViewModel>(private val mViewModel: VMType) :
    ViewModelProvider.Factory {

    @SuppressWarnings("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(mViewModel::class.java)) {
            return mViewModel as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}
