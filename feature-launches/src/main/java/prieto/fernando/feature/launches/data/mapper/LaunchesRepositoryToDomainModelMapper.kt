package prieto.fernando.feature.launches.data.mapper

import prieto.fernando.feature.launches.data.model.LaunchRepositoryModel
import prieto.fernando.feature.launches.domain.model.LaunchDomainModel
import prieto.fernando.feature.launches.domain.model.LinksDomainModel
import prieto.fernando.feature.launches.domain.model.RocketDomainModel
import javax.inject.Inject

interface LaunchesRepositoryToDomainModelMapper {
    fun toDomainModel(launchesRepositoryModel: List<LaunchRepositoryModel>): List<LaunchDomainModel>
}

class LaunchesRepositoryToDomainModelMapperImpl @Inject constructor() :
    LaunchesRepositoryToDomainModelMapper {
    override fun toDomainModel(
        launchesRepositoryModel: List<LaunchRepositoryModel>
    ): List<LaunchDomainModel> =
        launchesRepositoryModel.map { launchRepositoryModel ->
            val linksDomainModel = LinksDomainModel(
                missionPatchSmall = launchRepositoryModel.links.missionPatchSmall,
                wikipedia = launchRepositoryModel.links.wikipedia,
                videoLink = launchRepositoryModel.links.videoLink
            )

            val rocketDomainModel = RocketDomainModel(
                rocketName = launchRepositoryModel.rocket.rocketName,
                rocketType = launchRepositoryModel.rocket.rocketType
            )

            LaunchDomainModel(
                missionName = launchRepositoryModel.missionName,
                launchDate = launchRepositoryModel.launchDateLocal,
                rocket = rocketDomainModel,
                links = linksDomainModel,
                launchSuccess = launchRepositoryModel.launchSuccess
            )
        }
}

