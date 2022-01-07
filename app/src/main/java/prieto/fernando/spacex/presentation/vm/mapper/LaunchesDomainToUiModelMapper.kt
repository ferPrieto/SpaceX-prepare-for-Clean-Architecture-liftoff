package prieto.fernando.spacex.presentation.vm.mapper

import prieto.fernando.domain.model.LaunchDomainModel
import prieto.fernando.spacex.presentation.screens.launches.LaunchUiModel
import prieto.fernando.spacex.presentation.screens.launches.LinksUiModel
import prieto.fernando.spacex.presentation.screens.launches.RocketUiModel
import javax.inject.Inject

interface LaunchesDomainToUiModelMapper {
    fun toUiModel(
        launchesDomainModel: List<LaunchDomainModel>
    ): List<LaunchUiModel>
}

class LaunchesDomainToUiModelMapperImpl @Inject constructor(
    private val dateTransformer: DateTransformer
) : LaunchesDomainToUiModelMapper {
    override fun toUiModel(
        launchesDomainModel: List<LaunchDomainModel>
    ): List<LaunchUiModel> = launchesDomainModel.map { launchDomainModel ->
        val linksUiModel = LinksUiModel(
            missionPatchSmall = launchDomainModel.links.missionPatchSmall,
            wikipedia = launchDomainModel.links.wikipedia,
            youTubeLink = launchDomainModel.links.videoLink
        )

        val rocketUiModel = RocketUiModel(
            rocketName = launchDomainModel.rocket.rocketName,
            rocketType = launchDomainModel.rocket.rocketType
        )

        LaunchUiModel(
            missionName = launchDomainModel.missionName,
            launchDate = dateTransformer.dateToDateString(launchDomainModel.launchDate),
            rocketUiModel = rocketUiModel,
            linksUiModel = linksUiModel,
            launchSuccess = launchDomainModel.launchSuccess,
            isPastLaunch = dateTransformer.isPast(launchDomainModel.launchDate),
            differenceOfDays = dateTransformer.getDifferenceOfDays(launchDomainModel.launchDate)
        )
    }
}
