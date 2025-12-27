package ferprieto.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompanyInfoResponse(
    val name: String,
    val founder: String,
    val founded: Int,
    val employees: Int,
    @SerialName("launch_sites")
    val launchSites: Int,
    val valuation: Long
)
