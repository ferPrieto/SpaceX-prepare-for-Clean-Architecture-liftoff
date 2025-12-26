package prieto.fernando.feature.dashboard.presentation.mapper

import prieto.fernando.feature.dashboard.domain.model.CompanyInfoDomainModel
import prieto.fernando.feature.dashboard.presentation.CompanyInfoUiModel
import javax.inject.Inject

interface CompanyInfoDomainToUiModelMapper {
    fun toUiModel(companyInfoDomainModel: CompanyInfoDomainModel): CompanyInfoUiModel
}

class CompanyInfoDomainToUiModelMapperImpl @Inject constructor() :
    CompanyInfoDomainToUiModelMapper {
    override fun toUiModel(companyInfoDomainModel: CompanyInfoDomainModel): CompanyInfoUiModel =
        CompanyInfoUiModel(
            name = companyInfoDomainModel.name,
            founder = companyInfoDomainModel.founder,
            foundedYear = companyInfoDomainModel.founded,
            employees = companyInfoDomainModel.employees,
            launchSites = companyInfoDomainModel.launchSites,
            valuation = companyInfoDomainModel.valuation
        )
}

