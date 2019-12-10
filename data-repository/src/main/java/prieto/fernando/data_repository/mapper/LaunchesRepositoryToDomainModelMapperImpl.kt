package prieto.fernando.data_repository.mapper

import prieto.fernando.data_repository.model.LaunchesRepositoryModel
import prieto.fernando.domain.model.LaunchesDomainModel
import prieto.fernando.domain.model.LinksDomainModel
import prieto.fernando.domain.model.RocketDomainModel
import javax.inject.Inject

interface LaunchesRepositoryToDomainModelMapper {
    fun toDomainModel(launchesRepositoryModel: LaunchesRepositoryModel): LaunchesDomainModel
}

class LaunchesRepositoryToDomainModelMapperImpl @Inject constructor() :
    LaunchesRepositoryToDomainModelMapper {
    override fun toDomainModel(launchesRepositoryModel: LaunchesRepositoryModel): LaunchesDomainModel {
        val linksDomainModel = LinksDomainModel(
            missionPatchSmall = launchesRepositoryModel.links.missionPatchSmall,
            wikipedia = launchesRepositoryModel.links.wikipedia,
            videoLink = launchesRepositoryModel.links.videoLink
        )

        val rocketDomainModel = RocketDomainModel(
            rocketName = launchesRepositoryModel.rocket.rocketName,
            rocketType = launchesRepositoryModel.rocket.rocketType
        )

        return LaunchesDomainModel(
            missionName = launchesRepositoryModel.missionName,
            launchDateLocal = launchesRepositoryModel.launchDateLocal,
            rocket = rocketDomainModel,
            links = linksDomainModel,
            launchSuccess = launchesRepositoryModel.launchSuccess
        )
    }
}
