package prieto.fernando.spacex.presentation.vm.mapper

import prieto.fernando.domain.model.LaunchDomainModel
import prieto.fernando.spacex.presentation.launches.Launch
import prieto.fernando.spacex.presentation.launches.Links
import prieto.fernando.spacex.presentation.launches.Rocket
import javax.inject.Inject

interface LaunchesDomainToUiModelMapper {
    fun toUiModel(
        launchesDomainModel: List<LaunchDomainModel>
    ): List<Launch>
}

class LaunchesDomainToUiModelMapperImpl @Inject constructor(
    private val dateTransformer: DateTransformer
) : LaunchesDomainToUiModelMapper {
    override fun toUiModel(
        launchesDomainModel: List<LaunchDomainModel>
    ): List<Launch> = launchesDomainModel.map { launchDomainModel ->
        val linksUiModel = Links(
            missionPatchSmall = launchDomainModel.links.missionPatchSmall,
            wikipedia = launchDomainModel.links.wikipedia,
            videoLink = launchDomainModel.links.videoLink
        )

        val rocketUiModel = Rocket(
            rocketName = launchDomainModel.rocket.rocketName,
            rocketType = launchDomainModel.rocket.rocketType
        )

        Launch(
            missionName = launchDomainModel.missionName,
            launchDate = dateTransformer.dateToDateString(launchDomainModel.launchDate),
            rocket = rocketUiModel,
            links = linksUiModel,
            launchSuccess = launchDomainModel.launchSuccess,
            isPastLaunch = dateTransformer.isPast(launchDomainModel.launchDate),
            differenceOfDays = dateTransformer.getDifferenceOfDays(launchDomainModel.launchDate)
        )
    }
}
