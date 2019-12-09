package prieto.fernando.data_api.model

class LaunchesResponse (
    val mission_name : String,
    val launch_date_local : String,
    val rocket : RocketResponse,
    val links: LinksResponse,
    val launch_success : Boolean
)

class RocketResponse(
    val rocket_name :String,
    val rocket_type: String
)

class LinksResponse(
    val mission_patch_small : String,
    val wikipedia: String,
    val video_link: String
)