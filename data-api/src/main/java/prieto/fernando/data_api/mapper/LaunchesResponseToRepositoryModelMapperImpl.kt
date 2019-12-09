package prieto.fernando.data_api.mapper

import dagger.Reusable
import prieto.fernando.data_api.model.LaunchesResponse
import prieto.fernando.data_repository.model.LaunchesRepositoryModel
import prieto.fernando.data_repository.model.LinksRepositoryModel
import prieto.fernando.data_repository.model.RocketRepositoryModel
import javax.inject.Inject

interface CompanyLaunchesResponseToRepositoryModelMapper {
    fun toRepositoryModel(launchesResponse: LaunchesResponse): LaunchesRepositoryModel
}

@Reusable
class CompanyLaunchesResponseToRepositoryModelMapperImpl @Inject constructor() :
    CompanyLaunchesResponseToRepositoryModelMapper {
    override fun toRepositoryModel(launchesResponse: LaunchesResponse): LaunchesRepositoryModel {
        val linksRepositoryModel = LinksRepositoryModel(
            missionPatchSmall = launchesResponse.links.missionPatchSmall,
            wikipedia = launchesResponse.links.wikipedia,
            videoLink = launchesResponse.links.videoLink
        )

        val rocketRepositoryModel = RocketRepositoryModel(
            rocketName = launchesResponse.rocket.rocketName,
            rocketType = launchesResponse.rocket.rocketType
        )

        return LaunchesRepositoryModel(
            missionName = launchesResponse.missionName,
            launchDateLocal = launchesResponse.launchDateLocal,
            rocket = rocketRepositoryModel,
            links = linksRepositoryModel,
            launchSuccess = launchesResponse.launchSuccess
        )
    }
}
