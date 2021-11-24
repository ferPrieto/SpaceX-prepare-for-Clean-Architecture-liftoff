package prieto.fernando.spacex.presentation.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import prieto.fernando.spacex.R
import prieto.fernando.spacex.databinding.FragmentDashboardBinding
import prieto.fernando.spacex.presentation.vm.DashboardViewModel
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
        /*  viewModel.companyInfo.observe(viewLifecycleOwner, { companyInfo ->
              bindCompanyInfo(companyInfo)
            //  binding.dashboardAnimation.isVisible = true
              setErrorViewsVisibility(false)
          })
          viewModel.loadingCompanyInfo.observe(viewLifecycleOwner, { show ->
            //  binding.dashboardProgressBar.isVisible = show
          })
          viewModel.companyInfoError.observeEvent(this) {
              setErrorViewsVisibility(true)
             binding.dashboardAnimation.isVisible = false
          }*/
    }

    private fun setErrorViewsVisibility(show:Boolean){
        /* binding.dashboardErrorAnimation.isVisible = show
         binding.errorDescription.isVisible = show*/
    }

    override fun onResume() {
        super.onResume()
        viewModel.companyInfo()
    }

    private fun bindCompanyInfo(companyInfo: CompanyInfo?) {
        companyInfo?.let { companyInfo ->
            binding.companyDescription.text = getDescriptionText(companyInfo)
        }
    }

    private fun getDescriptionText(companyInfo: CompanyInfo): String {
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