package prieto.fernando.data_api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompanyInfoResponse(
    val name: String,
    val founder: String,
    val founded: String,
    val employees: String,
    @SerialName("launch_sites")
    val launchSites: Int,
    val valuation: Long
)
