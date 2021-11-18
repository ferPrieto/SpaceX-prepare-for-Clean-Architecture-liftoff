package prieto.fernando.spacex.presentation.vm.mapper

import prieto.fernando.domain.model.CompanyInfoDomainModel
import prieto.fernando.spacex.presentation.dashboard.CompanyInfo
import javax.inject.Inject

interface CompanyInfoDomainToUiModelMapper {
    fun toUiModel(companyInfoDomainModel: CompanyInfoDomainModel): CompanyInfo
}

class CompanyInfoDomainToUiModelMapperImpl @Inject constructor() :
    CompanyInfoDomainToUiModelMapper {
    override fun toUiModel(companyInfoDomainModel: CompanyInfoDomainModel): CompanyInfo =
        CompanyInfo(
            name = companyInfoDomainModel.name,
            founder = companyInfoDomainModel.founder,
            foundedYear = companyInfoDomainModel.founded,
            employees = companyInfoDomainModel.employees,
            launchSites = companyInfoDomainModel.launchSites,
            valuation = companyInfoDomainModel.valuation
        )
}
