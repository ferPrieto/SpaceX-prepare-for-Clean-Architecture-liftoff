package prieto.fernando.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LaunchesResponse(
    @SerialName("flight_number")
    val flightNumber: Int? = null,
    @SerialName("mission_name")
    val missionName: String? = null,
    @SerialName("mission_id")
    val missionId: List<String>? = null,
    @SerialName("launch_year")
    val launchYear: String? = null,
    @SerialName("launch_date_unix")
    val launchDateUnix: Long? = null,
    @SerialName("launch_date_utc")
    val launchDateUtc: String? = null,
    @SerialName("launch_date_local")
    val launchDateLocal: String? = null,
    @SerialName("is_tentative")
    val isTentative: Boolean? = null,
    @SerialName("tentative_max_precision")
    val tentativeMaxPrecision: String? = null,
    @SerialName("tbd")
    val tbd: Boolean? = null,
    @SerialName("launch_window")
    val launchWindow: Int? = null,
    @SerialName("rocket")
    val rocket: RocketResponse? = null,
    @SerialName("ships")
    val ships: List<String>? = null,
    @SerialName("telemetry")
    val telemetry: TelemetryResponse? = null,
    @SerialName("launch_site")
    val launchSite: LaunchSiteResponse? = null,
    @SerialName("launch_success")
    val launchSuccess: Boolean? = null,
    @SerialName("launch_failure_details")
    val launchFailureDetails: LaunchFailureDetailsResponse? = null,
    @SerialName("links")
    val links: LinksResponse? = null,
    @SerialName("details")
    val details: String? = null,
    @SerialName("upcoming")
    val upcoming: Boolean? = null,
    @SerialName("static_fire_date_utc")
    val staticFireDateUtc: String? = null,
    @SerialName("static_fire_date_unix")
    val staticFireDateUnix: Long? = null,
    @SerialName("timeline")
    val timeline: TimelineResponse? = null,
    @SerialName("crew")
    val crew: List<String>? = null
)

@Serializable
data class RocketResponse(
    @SerialName("rocket_id")
    val rocketId: String? = null,
    @SerialName("rocket_name")
    val rocketName: String? = null,
    @SerialName("rocket_type")
    val rocketType: String? = null,
    @SerialName("first_stage")
    val firstStage: FirstStageResponse? = null,
    @SerialName("second_stage")
    val secondStage: SecondStageResponse? = null,
    @SerialName("fairings")
    val fairings: FairingsResponse? = null
)

@Serializable
data class FirstStageResponse(
    @SerialName("cores")
    val cores: List<CoreResponse>? = null
)

@Serializable
data class CoreResponse(
    @SerialName("core_serial")
    val coreSerial: String? = null,
    @SerialName("flight")
    val flight: Int? = null,
    @SerialName("block")
    val block: Int? = null,
    @SerialName("gridfins")
    val gridfins: Boolean? = null,
    @SerialName("legs")
    val legs: Boolean? = null,
    @SerialName("reused")
    val reused: Boolean? = null,
    @SerialName("land_success")
    val landSuccess: Boolean? = null,
    @SerialName("landing_intent")
    val landingIntent: Boolean? = null,
    @SerialName("landing_type")
    val landingType: String? = null,
    @SerialName("landing_vehicle")
    val landingVehicle: String? = null
)

@Serializable
data class SecondStageResponse(
    @SerialName("block")
    val block: Int? = null,
    @SerialName("payloads")
    val payloads: List<PayloadResponse>? = null
)

@Serializable
data class PayloadResponse(
    @SerialName("payload_id")
    val payloadId: String? = null,
    @SerialName("norad_id")
    val noradId: List<Int>? = null,
    @SerialName("reused")
    val reused: Boolean? = null,
    @SerialName("customers")
    val customers: List<String>? = null,
    @SerialName("nationality")
    val nationality: String? = null,
    @SerialName("manufacturer")
    val manufacturer: String? = null,
    @SerialName("payload_type")
    val payloadType: String? = null,
    @SerialName("payload_mass_kg")
    val payloadMassKg: Double? = null,
    @SerialName("payload_mass_lbs")
    val payloadMassLbs: Double? = null,
    @SerialName("orbit")
    val orbit: String? = null,
    @SerialName("orbit_params")
    val orbitParams: OrbitParamsResponse? = null
)

@Serializable
data class OrbitParamsResponse(
    @SerialName("reference_system")
    val referenceSystem: String? = null,
    @SerialName("regime")
    val regime: String? = null,
    @SerialName("longitude")
    val longitude: Double? = null,
    @SerialName("semi_major_axis_km")
    val semiMajorAxisKm: Double? = null,
    @SerialName("eccentricity")
    val eccentricity: Double? = null,
    @SerialName("periapsis_km")
    val periapsisKm: Double? = null,
    @SerialName("apoapsis_km")
    val apoapsisKm: Double? = null,
    @SerialName("inclination_deg")
    val inclinationDeg: Double? = null,
    @SerialName("period_min")
    val periodMin: Double? = null,
    @SerialName("lifespan_years")
    val lifespanYears: Double? = null,
    @SerialName("epoch")
    val epoch: String? = null,
    @SerialName("mean_motion")
    val meanMotion: Double? = null,
    @SerialName("raan")
    val raan: Double? = null,
    @SerialName("arg_of_pericenter")
    val argOfPericenter: Double? = null,
    @SerialName("mean_anomaly")
    val meanAnomaly: Double? = null
)

@Serializable
data class FairingsResponse(
    @SerialName("reused")
    val reused: Boolean? = null,
    @SerialName("recovery_attempt")
    val recoveryAttempt: Boolean? = null,
    @SerialName("recovered")
    val recovered: Boolean? = null,
    @SerialName("ship")
    val ship: String? = null
)

@Serializable
data class LaunchSiteResponse(
    @SerialName("site_id")
    val siteId: String? = null,
    @SerialName("site_name")
    val siteName: String? = null,
    @SerialName("site_name_long")
    val siteNameLong: String? = null
)

@Serializable
data class LinksResponse(
    @SerialName("mission_patch")
    val missionPatch: String? = null,
    @SerialName("mission_patch_small")
    val missionPatchSmall: String? = null,
    @SerialName("reddit_campaign")
    val redditCampaign: String? = null,
    @SerialName("reddit_launch")
    val redditLaunch: String? = null,
    @SerialName("reddit_recovery")
    val redditRecovery: String? = null,
    @SerialName("reddit_media")
    val redditMedia: String? = null,
    @SerialName("presskit")
    val presskit: String? = null,
    @SerialName("article_link")
    val articleLink: String? = null,
    @SerialName("wikipedia")
    val wikipedia: String? = null,
    @SerialName("video_link")
    val videoLink: String? = null,
    @SerialName("youtube_id")
    val youtubeId: String? = null,
    @SerialName("flickr_images")
    val flickrImages: List<String>? = null
)

@Serializable
data class TelemetryResponse(
    @SerialName("flight_club")
    val flightClub: String? = null
)

@Serializable
data class LaunchFailureDetailsResponse(
    @SerialName("time")
    val time: Int? = null,
    @SerialName("altitude")
    val altitude: Int? = null,
    @SerialName("reason")
    val reason: String? = null
)

@Serializable
data class TimelineResponse(
    @SerialName("webcast_liftoff")
    val webcastLiftoff: Int? = null,
    @SerialName("go_for_prop_loading")
    val goForPropLoading: Int? = null,
    @SerialName("rp1_loading")
    val rp1Loading: Int? = null,
    @SerialName("stage1_lox_loading")
    val stage1LoxLoading: Int? = null,
    @SerialName("stage2_lox_loading")
    val stage2LoxLoading: Int? = null,
    @SerialName("engine_chill")
    val engineChill: Int? = null,
    @SerialName("prelaunch_checks")
    val prelaunchChecks: Int? = null,
    @SerialName("propellant_pressurization")
    val propellantPressurization: Int? = null,
    @SerialName("go_for_launch")
    val goForLaunch: Int? = null,
    @SerialName("ignition")
    val ignition: Int? = null,
    @SerialName("liftoff")
    val liftoff: Int? = null,
    @SerialName("maxq")
    val maxq: Int? = null,
    @SerialName("meco")
    val meco: Int? = null,
    @SerialName("stage_sep")
    val stageSep: Int? = null,
    @SerialName("second_stage_ignition")
    val secondStageIgnition: Int? = null,
    @SerialName("fairing_deploy")
    val fairingDeploy: Int? = null,
    @SerialName("first_stage_entry_burn")
    val firstStageEntryBurn: Int? = null,
    @SerialName("seco-1")
    val seco1: Int? = null,
    @SerialName("first_stage_landing")
    val firstStageLanding: Int? = null,
    @SerialName("beco")
    val beco: Int? = null,
    @SerialName("side_core_sep")
    val sideCoreSep: Int? = null,
    @SerialName("side_core_boostback")
    val sideCoreBoostback: Int? = null,
    @SerialName("center_stage_sep")
    val centerStageSep: Int? = null,
    @SerialName("center_core_boostback")
    val centerCoreBoostback: Int? = null,
    @SerialName("side_core_entry_burn")
    val sideCoreEntryBurn: Int? = null,
    @SerialName("center_core_entry_burn")
    val centerCoreEntryBurn: Int? = null,
    @SerialName("side_core_landing")
    val sideCoreLanding: Int? = null,
    @SerialName("center_core_landing")
    val centerCoreLanding: Int? = null,
    @SerialName("payload_deploy")
    val payloadDeploy: Int? = null,
    @SerialName("payload_deploy_1")
    val payloadDeploy1: Int? = null,
    @SerialName("payload_deploy_2")
    val payloadDeploy2: Int? = null,
    @SerialName("dragon_separation")
    val dragonSeparation: Int? = null,
    @SerialName("dragon_solar_deploy")
    val dragonSolarDeploy: Int? = null,
    @SerialName("dragon_bay_door_deploy")
    val dragonBayDoorDeploy: Int? = null
)
