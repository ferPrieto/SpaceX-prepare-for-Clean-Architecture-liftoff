package prieto.fernando.data_api.model

import com.google.gson.annotations.SerializedName

class LaunchesResponse(
    @SerializedName("mission_name")
    val missionName: String,
    @SerializedName("launch_date_local")
    val launchDateLocal: String,
    val rocket: RocketResponse,
    val links: LinksResponse,
    @SerializedName("launch_success")
    val launchSuccess: Boolean
)

class RocketResponse(
    @SerializedName("rocket_name")
    val rocketName: String,
    @SerializedName("rocket_type")
    val rocketType: String
)

class LinksResponse(
    @SerializedName("mission_patch_small")
    val missionPatchSmall: String,
    val wikipedia: String,
    @SerializedName("video_link")
    val videoLink: String
)
