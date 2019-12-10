package prieto.fernando.spacex.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.view_header.*
import prieto.fernando.core.ui.BaseFragment
import prieto.fernando.presentation.MainViewModel
import prieto.fernando.presentation.model.CompanyInfoUiModel
import prieto.fernando.presentation.model.LaunchUiModel
import prieto.fernando.spacex.R
import prieto.fernando.spacex.ui.adapter.ClickListener
import prieto.fernando.spacex.ui.adapter.LaunchesAdapter
import kotlinx.android.synthetic.main.view_body.launches_recycler_view as launchesRecyclerView

class MainFragment : BaseFragment<MainViewModel>(), ClickListener {

    private var launchesAdapter: LaunchesAdapter? = null

    override fun onItemClicked(url: String) {
        //todo: open link on VM
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_main, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        launchesAdapter = LaunchesAdapter(this)
        launchesRecyclerView.adapter = launchesAdapter
        val linearLayoutManager = LinearLayoutManager(context)
        launchesRecyclerView.layoutManager = linearLayoutManager
    }

    override fun onResume() {
        super.onResume()
        viewModel.launches()
        viewModel.companyInfo()
    }

    override val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, vmFactory).get(MainViewModel::class.java).apply {
            observe(onLaunchesUiModelRetrieved(), ::bindLaunches)
            observe(onCompanyInfoUiModelRetrieved(), ::bindCompany)
            observe(loading(), ::showLoading)
        }
    }

    private fun bindCompany(companyInfoUiModel: CompanyInfoUiModel?) {
        companyInfoUiModel?.let { companyInfo ->
            company_title.text = companyInfo.name
            company_description.text = getDescriptionText(companyInfo)
        }
    }

    private fun getDescriptionText(companyInfo: CompanyInfoUiModel): String {
        return String.format(
            getString(R.string.company_data),
            companyInfo.name,
            companyInfo.founder,
            companyInfo.foundedYear,
            companyInfo.employees,
            companyInfo.launchSites,
            companyInfo.valuation
        )
    }

    private fun bindLaunches(launchesUiModel: List<LaunchUiModel>?) {
        launchesUiModel?.let { launches ->
            launchesAdapter?.setData(launches)
        }
    }

    private fun showLoading(loading: Boolean?) {

    }

}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))