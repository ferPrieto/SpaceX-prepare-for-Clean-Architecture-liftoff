package ferprieto.feature.launches.data.mapper

import ferprieto.feature.launches.data.model.LaunchRepositoryModel
import ferprieto.feature.launches.domain.model.LaunchDomainModel
import ferprieto.feature.launches.domain.model.LinksDomainModel
import ferprieto.feature.launches.domain.model.RocketDomainModel
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

