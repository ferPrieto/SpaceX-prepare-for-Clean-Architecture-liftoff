package prieto.fernando.spacex.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import prieto.fernando.presentation.model.LaunchUiModel
import prieto.fernando.spacex.R
import kotlinx.android.synthetic.main.view_launch_data.view.launch_details_date_time as launchDetailsDateTime
import kotlinx.android.synthetic.main.view_launch_data.view.launch_details_days as launchDetailsDays
import kotlinx.android.synthetic.main.view_launch_data.view.launch_details_mission as launchDetailsMission
import kotlinx.android.synthetic.main.view_launch_data.view.launch_details_name_time as launchDetailsNameTime

interface BindableAdapter<T> {
    fun setData(data: T)
}

interface ClickListener {
    fun onItemClicked(url: String)
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
            //todo itemView.missionPatch.drawable
            //todo itemView.view_success = drawable with boolean
            itemView.launchDetailsMission.value = launchUiModel.missionName
            itemView.launchDetailsDateTime.value = launchUiModel.launchDate
            itemView.launchDetailsNameTime.value =
                "${launchUiModel.rocket.rocketName}/${launchUiModel.rocket.rocketType}"
            itemView.launchDetailsDays.title = getTitleSinceFrom(launchUiModel, itemView.context)
            itemView.launchDetailsDays.value = "+/-${launchUiModel.differenceOfDays}"
            itemView.setOnClickListener {
                clickListener.onItemClicked(getAvailableLink(launchUiModel))
            }
        }

        private fun getTitleSinceFrom(launchUiModel: LaunchUiModel, context: Context) =
            if (launchUiModel.isPastLaunch) {
                context.getString(R.string.company_data_since)
            } else {
                context.getString(R.string.company_data_from)
            }

        private fun getAvailableLink(launchUiModel: LaunchUiModel) = with(launchUiModel.links) {
            when {
                videoLink.isNotBlank() -> videoLink
                wikipedia.isNotBlank() -> wikipedia
                else -> ""
            }
        }
    }
}
