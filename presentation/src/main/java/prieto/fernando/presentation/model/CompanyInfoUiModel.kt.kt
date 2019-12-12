package prieto.fernando.presentation.model

data class CompanyInfoUiModel(
    val name: String,
    val founder: String,
    val foundedYear: String,
    val employees: String,
    val launchSites: Int,
    val valuation: Long
)