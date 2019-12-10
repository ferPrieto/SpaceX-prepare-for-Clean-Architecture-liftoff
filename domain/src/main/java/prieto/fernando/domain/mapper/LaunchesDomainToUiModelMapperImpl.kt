package prieto.fernando.domain.mapper

import prieto.fernando.domain.model.LaunchDomainModel
import prieto.fernando.presentation.model.LaunchUiModel
import prieto.fernando.presentation.model.LinksUiModel
import prieto.fernando.presentation.model.RocketUiModel
import javax.inject.Inject

interface LaunchesDomainToUiModelMapper {
    fun toUiModel(launchesDomainModel: List<LaunchDomainModel>): List<LaunchUiModel>
}

class LaunchesDomainToUiModelMapperImpl @Inject constructor() :
    LaunchesDomainToUiModelMapper {
    override fun toUiModel(launchesDomainModel: List<LaunchDomainModel>): List<LaunchUiModel> =
        launchesDomainModel.map { launchDomainModel ->
            val linksUiModel = LinksUiModel(
                missionPatchSmall = launchDomainModel.links.missionPatchSmall,
                wikipedia = launchDomainModel.links.wikipedia,
                videoLink = launchDomainModel.links.videoLink
            )

            val rocketUiModel = RocketUiModel(
                rocketName = launchDomainModel.rocket.rocketName,
                rocketType = launchDomainModel.rocket.rocketType
            )

            LaunchUiModel(
                missionName = launchDomainModel.missionName,
                launchDateLocal = launchDomainModel.launchDateLocal,
                rocket = rocketUiModel,
                links = linksUiModel,
                launchSuccess = launchDomainModel.launchSuccess
            )
        }
}
