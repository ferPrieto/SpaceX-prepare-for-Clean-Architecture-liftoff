package prieto.fernando.domain.usecase

import dagger.Reusable
import io.reactivex.Single
import prieto.fernando.domain.SpaceXRepository
import prieto.fernando.domain.mapper.CompanyInfoDomainToUiModelMapper
import prieto.fernando.presentation.GetCompanyInfo
import prieto.fernando.presentation.model.CompanyInfoUiModel
import javax.inject.Inject

@Reusable
class GetCompanyInfoImpl @Inject constructor(
    private val spaceXRepository: SpaceXRepository,
    private val companyInfoDomainToUiModelMapper: CompanyInfoDomainToUiModelMapper
) : GetCompanyInfo {
    override fun execute(): Single<CompanyInfoUiModel> = spaceXRepository.getCompanyInfo()
        .map(companyInfoDomainToUiModelMapper::toUiModel)
}
