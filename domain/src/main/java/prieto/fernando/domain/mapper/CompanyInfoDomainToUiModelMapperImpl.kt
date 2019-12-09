package prieto.fernando.domain.mapper

import dagger.Reusable
import prieto.fernando.domain.model.CompanyInfoDomainModel
import prieto.fernando.presentation.model.CompanyInfoUiModel
import javax.inject.Inject

interface CompanyInfoDomainToUiModelMapper {
    fun toUiModel(companyInfoDomainModel: CompanyInfoDomainModel): CompanyInfoUiModel
}

@Reusable
class CompanyInfoDomainToUiModelMapperImpl @Inject constructor() :
    CompanyInfoDomainToUiModelMapper {
    override fun toUiModel(companyInfoDomainModel: CompanyInfoDomainModel): CompanyInfoUiModel =
        CompanyInfoUiModel(
            name = companyInfoDomainModel.name,
            founder = companyInfoDomainModel.founder,
            founded = companyInfoDomainModel.founded,
            employees = companyInfoDomainModel.employees,
            launchSites = companyInfoDomainModel.launchSites,
            valuation = companyInfoDomainModel.valuation
        )
}
