package prieto.fernando.feature.launches.presentation.vm.mapper

import prieto.fernando.feature.launches.domain.model.LaunchDomainModel
import prieto.fernando.feature.launches.presentation.ui.LaunchUiModel
import prieto.fernando.feature.launches.presentation.ui.LinksUiModel
import prieto.fernando.feature.launches.presentation.ui.RocketUiModel
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
        with(launchDomainModel) {
            val linksUiModel = LinksUiModel(
                missionPatchSmall = links.missionPatchSmall,
                wikipedia = links.wikipedia,
                youTubeLink = links.videoLink
            )

            val rocketUiModel = RocketUiModel(
                rocketName = rocket.rocketName,
                rocketType = rocket.rocketType
            )

            LaunchUiModel(
                missionName = missionName,
                launchDate = launchDate?.let { dateTransformer.dateToDateString(it) } ?: "",
                rocketUiModel = rocketUiModel,
                linksUiModel = linksUiModel,
                launchSuccess = launchSuccess,
                isPastLaunch = launchDate?.let { dateTransformer.isPast(it) } ?: false,
                differenceOfDays = launchDate?.let { dateTransformer.getDifferenceOfDays(it) } ?: ""
            )
        }
    }
}
