package prieto.fernando.domain.mapper

import prieto.fernando.domain.model.LaunchDomainModel
import javax.inject.Inject

interface LaunchesDomainFilter {
    fun filter(
        launchesDomainModel: List<LaunchDomainModel>,
        filterYear: Int,
        ascendantOrder: Boolean
    ): List<LaunchDomainModel>
}

class LaunchesDomainFilterImpl @Inject constructor() : LaunchesDomainFilter {
    override fun filter(
        launchesDomainModel: List<LaunchDomainModel>,
        filterYear: Int,
        ascendantOrder: Boolean
    ): List<LaunchDomainModel> = if (filterYear > 0) {
        val launches = launchesDomainModel.filter { launchDomainModel ->
            launchDomainModel.launchDate.year == filterYear
        }.filter { launchDomainModel ->
            launchDomainModel.launchSuccess
        }
        order(launches, ascendantOrder)
    } else {
        launchesDomainModel
    }

    private fun order(
        launches: List<LaunchDomainModel>,
        ascendantOrder: Boolean
    ): List<LaunchDomainModel> = if (ascendantOrder) {
        launches.sortedBy { launchDomainModel ->
            launchDomainModel.launchDate.monthOfYear
        }.sortedBy { launchDomainModel ->
            launchDomainModel.launchDate.dayOfMonth
        }
    } else {
        launches.sortedByDescending { launchDomainModel ->
            launchDomainModel.launchDate.monthOfYear
        }.sortedByDescending { launchDomainModel ->
            launchDomainModel.launchDate.dayOfMonth
        }
    }
}
