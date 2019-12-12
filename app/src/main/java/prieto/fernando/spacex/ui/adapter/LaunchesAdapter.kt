package prieto.fernando.spacex.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import prieto.fernando.presentation.model.LaunchUiModel
import prieto.fernando.spacex.R
import prieto.fernando.spacex.ui.Link
import prieto.fernando.spacex.ui.LinkType
import kotlinx.android.synthetic.main.item_launch.view.launch_details_date_time as launchDetailsDateTime
import kotlinx.android.synthetic.main.item_launch.view.launch_details_days as launchDetailsDays
import kotlinx.android.synthetic.main.item_launch.view.launch_details_mission as launchDetailsMission
import kotlinx.android.synthetic.main.item_launch.view.launch_details_name_time as launchDetailsNameTime
import kotlinx.android.synthetic.main.item_launch.view.mission_patch as missionPatch
import kotlinx.android.synthetic.main.item_launch.view.view_success as viewSuccess

interface BindableAdapter<T> {
    fun setData(data: T)
}

interface ClickListener {
    fun onItemClicked(urls: Link)
}

class LaunchesAdapter(private val clickListener: ClickListener) :
    RecyclerView.Adapter<LaunchesAdapter.LaunchHolder>(),
    BindableAdapter<List<LaunchUiModel>> {
    private val launches = mutableListOf<LaunchUiModel>()

    override fun setData(data: List<LaunchUiModel>) {
        launches.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchHolder {
        val inflater = LayoutInflater.from(parent.context)
        return LaunchHolder(inflater.inflate(R.layout.item_launch, parent, false))
    }

    override fun getItemCount() = launches.size

    override fun onBindViewHolder(holder: LaunchHolder, position: Int) {
        holder.bind(launches[position], clickListener)
    }

    class LaunchHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(launchUiModel: LaunchUiModel, clickListener: ClickListener) {

            Picasso.get()
                .load(launchUiModel.links.missionPatchSmall)
                .resize(150, 150)
                .centerCrop()
                .into(itemView.missionPatch)
            itemView.launchDetailsMission.value = launchUiModel.missionName
            itemView.launchDetailsDateTime.value = launchUiModel.launchDate
            itemView.launchDetailsNameTime.value =
                "${launchUiModel.rocket.rocketName}/${launchUiModel.rocket.rocketType}"
            itemView.launchDetailsDays.title =
                getTitleSinceFrom(launchUiModel.isPastLaunch, itemView.context)
            itemView.launchDetailsDays.value = "+/-${launchUiModel.differenceOfDays}"
            val successDrawable = getSuccessDrawable(launchUiModel.launchSuccess, itemView.context)
            itemView.viewSuccess.setImageDrawable(successDrawable)

            itemView.setOnClickListener {
                clickListener.onItemClicked(getAvailableLinks(launchUiModel))
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
}
