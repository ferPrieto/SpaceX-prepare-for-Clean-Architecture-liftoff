package prieto.fernando.spacex.presentation.dashboard

data class CompanyInfo(
    val name: String,
    val founder: String,
    val foundedYear: String,
    val employees: String,
    val launchSites: Int,
    val valuation: Long
)