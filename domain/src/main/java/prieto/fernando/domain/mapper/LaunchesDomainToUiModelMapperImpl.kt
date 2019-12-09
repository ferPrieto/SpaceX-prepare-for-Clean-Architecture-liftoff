package prieto.fernando.domain.mapper

import dagger.Reusable
import prieto.fernando.domain.model.LaunchesDomainModel
import prieto.fernando.presentation.model.LaunchesUiModel
import prieto.fernando.presentation.model.LinksUiModel
import prieto.fernando.presentation.model.RocketUiModel
import javax.inject.Inject

interface LaunchesDomainToUiModelMapper {
    fun toUiModel(launchesDomainModel: LaunchesDomainModel): LaunchesUiModel
}

@Reusable
class LaunchesDomainToUiModelMapperImpl @Inject constructor() :
    LaunchesDomainToUiModelMapper {
    override fun toUiModel(launchesDomainModel: LaunchesDomainModel): LaunchesUiModel {
        val linksUiModel = LinksUiModel(
            missionPatchSmall = launchesDomainModel.links.missionPatchSmall,
            wikipedia = launchesDomainModel.links.wikipedia,
            videoLink = launchesDomainModel.links.videoLink
        )

        val rocketUiModel = RocketUiModel(
            rocketName = launchesDomainModel.rocket.rocketName,
            rocketType = launchesDomainModel.rocket.rocketType
        )

        return LaunchesUiModel(
            missionName = launchesDomainModel.missionName,
            launchDateLocal = launchesDomainModel.launchDateLocal,
            rocket = rocketUiModel,
            links = linksUiModel,
            launchSuccess = launchesDomainModel.launchSuccess
        )
    }
}
