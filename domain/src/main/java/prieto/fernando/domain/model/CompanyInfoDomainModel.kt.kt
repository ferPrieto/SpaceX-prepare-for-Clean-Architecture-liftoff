package prieto.fernando.domain.model

data class CompanyInfoDomainModel(
    val name: String,
    val founder: String,
    val founded: String,
    val employees: String,
    val launchSites: Int,
    val valuation: Long
)