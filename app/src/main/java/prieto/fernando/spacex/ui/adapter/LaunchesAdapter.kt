package prieto.fernando.spacex.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mitteloupe.solid.recyclerview.InflatedViewProvider
import com.mitteloupe.solid.recyclerview.SimpleViewBinder
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import prieto.fernando.presentation.model.LaunchUiModel
import prieto.fernando.spacex.R
import prieto.fernando.spacex.ui.Link
import prieto.fernando.spacex.ui.LinkType
import prieto.fernando.spacex.ui.widget.LaunchDetailsView
import kotlinx.android.synthetic.main.item_launch.view.card_container as cardContainer
import kotlinx.android.synthetic.main.item_launch.view.launch_details_date_time as launchDetailsDateTime
import kotlinx.android.synthetic.main.item_launch.view.launch_details_days as launchDetailsDays
import kotlinx.android.synthetic.main.item_launch.view.launch_details_mission as launchDetailsMission
import kotlinx.android.synthetic.main.item_launch.view.launch_details_name_time as launchDetailsNameTime
import kotlinx.android.synthetic.main.item_launch.view.mission_patch as missionPatch
import kotlinx.android.synthetic.main.item_launch.view.view_success as viewSuccess

interface ClickListener {
    fun onItemClicked(urls: Link)
}

class LaunchViewProvider(
    layoutInflater: LayoutInflater
) : InflatedViewProvider(layoutInflater, R.layout.item_launch)

class LaunchViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {
    val launchDetailsDateTime: LaunchDetailsView = containerView.launchDetailsDateTime
    val launchDetailsDays: LaunchDetailsView = containerView.launchDetailsDays
    val launchDetailsMission: LaunchDetailsView = containerView.launchDetailsMission
    val launchDetailsNameTime: LaunchDetailsView = containerView.launchDetailsNameTime
    val missionPatch: ImageView = containerView.missionPatch
    val viewSuccess: ImageView = containerView.viewSuccess
    val cardContainer: CardView = containerView.cardContainer
}

class LaunchViewBinder(
    private val context: Context,
    private val clickListener: ClickListener
) : SimpleViewBinder<LaunchViewHolder, LaunchUiModel>() {
    override fun bindView(viewHolder: LaunchViewHolder, data: LaunchUiModel) {

        Picasso.get()
            .load(data.links.missionPatchSmall)
            .resize(150, 150)
            .centerCrop()
            .into(viewHolder.missionPatch)
        viewHolder.launchDetailsMission.value = data.missionName
        viewHolder.launchDetailsDateTime.value = data.launchDate
        viewHolder.launchDetailsNameTime.value =
            "${data.rocket.rocketName}/${data.rocket.rocketType}"
        viewHolder.launchDetailsDays.title =
            getTitleSinceFrom(data.isPastLaunch, context)
        viewHolder.launchDetailsDays.value = "+/-${data.differenceOfDays}"
        val successDrawable = getSuccessDrawable(data.launchSuccess, context)
        viewHolder.viewSuccess.setImageDrawable(successDrawable)

        viewHolder.cardContainer.setOnClickListener {
            clickListener.onItemClicked(getAvailableLinks(data))
        }
    }

    private fun getTitleSinceFrom(isPastLaunch: Boolean, context: Context) =
        if (isPastLaunch) {
            context.getString(R.string.company_data_since)
        } else {
            context.getString(R.string.company_data_from)
        }

    private fun getAvailableLinks(launchUiModel: LaunchUiModel) = with(launchUiModel.links) {
        when {
            wikipedia.isNotBlank() && videoLink.isNotBlank() -> getLinksFromUrls(
                videoLink,
                wikipedia
            )
            wikipedia.isBlank() && videoLink.isNotBlank() -> getLinkFromUrl(
                videoLink,
                LinkType.YOUTUBE
            )
            videoLink.isBlank() && wikipedia.isNotBlank() -> getLinkFromUrl(
                videoLink,
                LinkType.WIKIPEDIA
            )
            else -> Link.Empty(Unit)
        }
    }

    private fun getLinkFromUrl(url: String, linkType: LinkType) = Link.OneLink(linkType, url)
    private fun getLinksFromUrls(linkYoutube: String, linkWikipedia: String) =
        Link.TwoLinks(linkYoutube, linkWikipedia)

    private fun getSuccessDrawable(launchSuccess: Boolean, context: Context) =
        if (launchSuccess) {
            context.getDrawable(R.drawable.ic_check)
        } else {
            context.getDrawable(R.drawable.ic_clear)
        }

}