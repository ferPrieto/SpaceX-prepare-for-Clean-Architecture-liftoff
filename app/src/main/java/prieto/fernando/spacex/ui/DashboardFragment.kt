package prieto.fernando.spacex.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.filter_toolbar.*
import prieto.fernando.core.event.observeEvent
import prieto.fernando.presentation.DashboardViewModel
import prieto.fernando.presentation.model.CompanyInfoUiModel
import prieto.fernando.spacex.R
import prieto.fernando.spacex.databinding.FragmentDashboardBinding
import javax.inject.Inject

class DashboardFragment @Inject constructor(
    viewModelFactory: ViewModelProvider.Factory
) : Fragment(R.layout.fragment_dashboard) {
    private val viewModel by viewModels<DashboardViewModel> { viewModelFactory }

    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModelObservers()
    }

    private fun setViewModelObservers() {
        viewModel.companyInfo.observe(viewLifecycleOwner, { companyInfo ->
            bindCompanyInfo(companyInfo)
            binding.dashboardAnimation.isVisible = true
        })
        viewModel.loadingHeader.observe(viewLifecycleOwner, { show ->
            binding.progressBarHeader.isVisible = show
        })
        viewModel.headerError.observeEvent(this) {
            binding.headerErrorDescription.isVisible = true
            binding.dashboardErrorAnimation.isVisible = true
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.companyInfo()
    }

    private fun bindCompanyInfo(companyInfoUiModel: CompanyInfoUiModel?) {
        companyInfoUiModel?.let { companyInfo ->
            binding.companyDescription.text = getDescriptionText(companyInfo)
            binding.headerErrorDescription.isVisible = false
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
}