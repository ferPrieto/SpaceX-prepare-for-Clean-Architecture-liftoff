package prieto.fernando.feature.launches.data.mapper

import prieto.fernando.core.network.mapper.DateFormatter
import prieto.fernando.core.network.model.LaunchesResponse
import prieto.fernando.feature.launches.data.model.LaunchRepositoryModel
import prieto.fernando.feature.launches.data.model.LinksRepositoryModel
import prieto.fernando.feature.launches.data.model.RocketRepositoryModel
import javax.inject.Inject

interface LaunchesResponseToRepositoryMapper {
    fun toRepositoryModel(launchesResponse: List<LaunchesResponse>): List<LaunchRepositoryModel>
}

const val DEFAULT_PATCH = "https://images2.imgbox.com/3c/0e/T8iJcSN3_o.png"

class LaunchesResponseToRepositoryMapperImpl @Inject constructor(
    private val dateFormatter: DateFormatter
) : LaunchesResponseToRepositoryMapper {
    override fun toRepositoryModel(
        launchesResponse: List<LaunchesResponse>
    ): List<LaunchRepositoryModel> {
        return launchesResponse.map { launchResponse ->

            val linksRepositoryModel = LinksRepositoryModel(
                missionPatchSmall = launchResponse.links?.missionPatchSmall ?: DEFAULT_PATCH,
                wikipedia = launchResponse.links?.wikipedia.orEmpty(),
                videoLink = launchResponse.links?.videoLink.orEmpty()
            )

            val rocketRepositoryModel = RocketRepositoryModel(
                rocketName = launchResponse.rocket?.rocketName.orEmpty(),
                rocketType = launchResponse.rocket?.rocketType.orEmpty()
            )

            LaunchRepositoryModel(
                missionName = launchResponse.missionName.orEmpty(),
                launchDateLocal = launchResponse.launchDateUtc?.let { dateFormatter.format(it) },
                rocket = rocketRepositoryModel,
                links = linksRepositoryModel,
                launchSuccess = launchResponse.launchSuccess ?: false
            )
        }
    }
}

