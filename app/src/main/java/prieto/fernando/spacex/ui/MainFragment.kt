package prieto.fernando.spacex.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import prieto.fernando.core.ui.BaseFragment
import prieto.fernando.presentation.MainViewModel
import prieto.fernando.presentation.model.CompanyInfoUiModel
import prieto.fernando.presentation.model.LaunchesUiModel
import prieto.fernando.spacex.R

class MainFragment : BaseFragment<MainViewModel>() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_main, container, false)!!

    override fun onResume() {
        super.onResume()
        viewModel
    }

    override val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, vmFactory).get(MainViewModel::class.java).apply {
            observe(onLaunchesUiModelRetrieved(), ::bindLaunches)
            observe(onCompanyInfoUiModelRetrieved(), ::bindCompany)
            observe(loading(), ::showLoading)
        }
    }

    private fun bindCompany(companyInfoUiModel: CompanyInfoUiModel?) {

    }

    private fun bindLaunches(launchesUiModel: LaunchesUiModel?) {

    }

    private fun showLoading(loading: Boolean?) {

    }

}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))