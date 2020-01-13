package prieto.fernando.spacex.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.mitteloupe.solid.recyclerview.SolidAdapter
import kotlinx.android.synthetic.main.filter_toolbar.*
import prieto.fernando.core.event.observeEvent
import prieto.fernando.presentation.MainViewModel
import prieto.fernando.presentation.model.CompanyInfoUiModel
import prieto.fernando.presentation.model.LaunchUiModel
import prieto.fernando.spacex.R
import prieto.fernando.spacex.ui.adapter.ClickListener
import prieto.fernando.spacex.ui.adapter.LaunchViewBinder
import prieto.fernando.spacex.ui.adapter.LaunchViewHolder
import prieto.fernando.spacex.ui.adapter.LaunchViewProvider
import prieto.fernando.spacex.ui.util.UrlUtils
import javax.inject.Inject
import kotlinx.android.synthetic.main.view_body.body_error_description as bodyErrorDescription
import kotlinx.android.synthetic.main.view_body.launches_recycler_view as launchesRecyclerView
import kotlinx.android.synthetic.main.view_body.progress_bar_body as progressBarBody
import kotlinx.android.synthetic.main.view_bottom_sheet.bottom_sheet as bottomSheet
import kotlinx.android.synthetic.main.view_bottom_sheet.wikipedia_icon as wikipediaIcon
import kotlinx.android.synthetic.main.view_bottom_sheet.wikipedia_title as wikipediaTitle
import kotlinx.android.synthetic.main.view_bottom_sheet.youtube_icon as youtubeIcon
import kotlinx.android.synthetic.main.view_bottom_sheet.youtube_title as youtubeTitle
import kotlinx.android.synthetic.main.view_header.company_description as companyDescription
import kotlinx.android.synthetic.main.view_header.header_error_description as headerErrorDescription
import kotlinx.android.synthetic.main.view_header.progress_bar_header as progressBarHeader


class DashboardFragment @Inject constructor(
    viewModelFactory: ViewModelProvider.Factory
) : Fragment() {
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    private var launchesAdapter: SolidAdapter<LaunchViewHolder, LaunchUiModel>? = null
    private var linkYoutube = ""
    private var linkWikipedia = ""
    private val clickListener = object : ClickListener {
        override fun onItemClicked(urls: Link) {
            when (urls) {
                is Link.OneLink -> showOneOptionSheet(urls)
                is Link.TwoLinks -> showTwoOptionsSheet(urls)
                else -> hideSheet()
            }
            expandOrCollapseBottomSheet()
        }
    }

    private fun expandOrCollapseBottomSheet() {
        if (bottomSheet.isExpended()) {
            bottomSheet.collapse()
        } else {
            bottomSheet.expand()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_dashboard, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupBottomSheet()
        setupNavigation()
        setupToolbarFilter()
        setViewModelObservers()
    }

    private fun setupNavigation() {
        requireActivity().let { fragmentActivity ->
            (fragmentActivity as AppCompatActivity).setSupportActionBar(toolbar)
            NavigationUI.setupActionBarWithNavController(
                fragmentActivity, findNavController()
            )
        }
    }

    private fun setupToolbarFilter() {
        filter.setOnClickListener {
            viewModel.onFilterClicked()
        }
    }

    private fun setViewModelObservers() {
        viewModel.launches.observe(this, Observer { launches ->
            bindLaunches(launches)
        })
        viewModel.companyInfo.observe(this, Observer { companyInfo ->
            bindCompanyInfo(companyInfo)
        })
        viewModel.loadingHeader.observe(this, Observer { show ->
            showLoadingHeader(show)
        })
        viewModel.loadingBody.observe(this, Observer { show ->
            showLoadingBody(show)
        })
        viewModel.openLink.observeEvent(this) { link ->
            openLink(link)
        }
        viewModel.showDialog.observeEvent(this) {
            showDialog()
        }
        viewModel.headerError.observeEvent(this) {
            showHeaderError()
        }
        viewModel.bodyError.observeEvent(this) {
            showBodyError()
        }
    }

    private fun setupBottomSheet() {
        bottomSheet.animationDuration = 500
        youtubeIcon.setOnClickListener { viewModel.openLink(linkYoutube) }
        youtubeTitle.setOnClickListener { viewModel.openLink(linkYoutube) }
        wikipediaIcon.setOnClickListener { viewModel.openLink(linkWikipedia) }
        wikipediaTitle.setOnClickListener { viewModel.openLink(linkWikipedia) }
    }

    private fun setupRecyclerView() {
        launchesAdapter = SolidAdapter(
            LaunchViewProvider(layoutInflater),
            { view, _ -> LaunchViewHolder(view) },
            LaunchViewBinder(context = context!!, clickListener = clickListener)
        )
        launchesRecyclerView.adapter = launchesAdapter
        val linearLayoutManager = LinearLayoutManager(context)
        launchesRecyclerView.layoutManager = linearLayoutManager
        launchesRecyclerView.setOnScrollChangeListener { _, _, _, _, _ ->
            bottomSheet.collapse()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.launches()
        viewModel.companyInfo()
    }

    private fun bindCompanyInfo(companyInfoUiModel: CompanyInfoUiModel?) {
        companyInfoUiModel?.let { companyInfo ->
            companyDescription.text = getDescriptionText(companyInfo)
            headerErrorDescription.isVisible = false
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
            launchesAdapter?.setItems(launches)
            bodyErrorDescription.isVisible = false
        }
    }

    private fun showLoadingBody(loading: Boolean) {
        progressBarBody.isVisible = loading
    }

    private fun showLoadingHeader(loading: Boolean) {
        progressBarHeader.isVisible = loading
    }

    private fun showTwoOptionsSheet(link: Link.TwoLinks) {
        setItemsVisibility(showYoutube = true, showWikipedia = true)
        linkYoutube = link.linkYoutube
        linkWikipedia = link.linkWikipedia
    }

    private fun showOneOptionSheet(oneLink: Link.OneLink) {
        when (oneLink.linkType) {
            LinkType.YOUTUBE -> showYoutube(oneLink.link)
            LinkType.WIKIPEDIA -> showWikipedia(oneLink.link)
        }
    }

    private fun showYoutube(url: String) {
        linkYoutube = url
        setItemsVisibility(showYoutube = true, showWikipedia = false)
    }

    private fun showWikipedia(url: String) {
        linkWikipedia = url
        setItemsVisibility(showYoutube = false, showWikipedia = true)
    }

    private fun setItemsVisibility(showYoutube: Boolean, showWikipedia: Boolean) {
        wikipediaIcon.isVisible = showWikipedia
        wikipediaTitle.isVisible = showWikipedia
        youtubeIcon.isVisible = showYoutube
        youtubeTitle.isVisible = showYoutube
    }

    private fun hideSheet() {
        bottomSheet.collapse()
    }

    private fun openLink(link: String) {
        UrlUtils.navigateTo(activity as Context, link)
    }

    private fun showBodyError() {
        bodyErrorDescription.isVisible = true
        launchesRecyclerView.isVisible = false
    }

    private fun showHeaderError() {
        headerErrorDescription.isVisible = true
        launchesRecyclerView.isVisible = false
    }

    private fun showDialog() {
        val dialog = layoutInflater.inflate(R.layout.view_dialog, null)
        val orderToggle = dialog.findViewById<Switch>(R.id.order_toggle)
        val yearEditText = dialog.findViewById<EditText>(R.id.dialog_year)

        val dialogBuilder = setUpDialogBuilder(orderToggle, yearEditText, dialog)
        dialogBuilder.show()
    }

    private fun setUpDialogBuilder(orderToggle: Switch, yearEditText: EditText, dialog: View) =
        AlertDialog.Builder(context).apply {
            setPositiveButton(
                getString(R.string.dialog_ok_button)
            ) { _, _ ->
                requestFilteredData(orderToggle, yearEditText)
            }
            setNegativeButton(
                getString(R.string.dialog_cancel_button)
            ) { dialog, _ ->
                dialog.dismiss()
            }
            setView(dialog)
        }

    private fun requestFilteredData(orderToggle: Switch, yearEditText: EditText) {
        val ascendant = orderToggle.isChecked
        val yearValue = yearEditText.text.toString()
        val year = Integer.parseInt(integerValueOrZero(yearValue))
        viewModel.launches(year, ascendant)
    }

    private fun integerValueOrZero(yearValue: String) =
        if (yearValue.isNotBlank()) {
            yearValue
        } else {
            "0"
        }
}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))

sealed class Link {
    data class OneLink(val linkType: LinkType, val link: String) : Link()
    data class TwoLinks(val linkYoutube: String, val linkWikipedia: String) : Link()
    data class Empty(val unit: Unit) : Link()
}

enum class LinkType {
    YOUTUBE,
    WIKIPEDIA
}