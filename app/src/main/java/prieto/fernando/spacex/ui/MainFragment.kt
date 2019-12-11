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
import prieto.fernando.core.ui.BaseFragment
import prieto.fernando.presentation.MainViewModel
import prieto.fernando.presentation.model.CompanyInfoUiModel
import prieto.fernando.presentation.model.LaunchUiModel
import prieto.fernando.spacex.R
import prieto.fernando.spacex.ui.adapter.ClickListener
import prieto.fernando.spacex.ui.adapter.LaunchesAdapter
import kotlinx.android.synthetic.main.view_body.launches_recycler_view as launchesRecyclerView
import kotlinx.android.synthetic.main.view_body.progress_bar_body as progressBarBody
import kotlinx.android.synthetic.main.view_error.error_title as errorTitle
import kotlinx.android.synthetic.main.view_error.error_description as errorDescription
import kotlinx.android.synthetic.main.view_header.company_description as companyDescription
import kotlinx.android.synthetic.main.view_header.progress_bar_header as progressBarHeader

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
            observe(loadingHeader(), ::showLoadingHeader)
            observe(loadingBody(), ::showLoadingBody)
            observe(error(), ::setViewsVisibility)
        }
    }

    private fun bindCompany(companyInfoUiModel: CompanyInfoUiModel?) {
        companyInfoUiModel?.let { companyInfo ->
            companyDescription.text = getDescriptionText(companyInfo)
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

    private fun showLoadingBody(loading: Boolean?) {
        loading?.let {
            progressBarBody.visibility = if (loading) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun showLoadingHeader(loading: Boolean?) {
        loading?.let {
            progressBarHeader.visibility = if (loading) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun setViewsVisibility(unit: Unit?) {
        errorTitle.visibility = View.VISIBLE
        errorDescription.visibility = View.VISIBLE
        launchesRecyclerView.visibility = View.GONE
    }
}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))
