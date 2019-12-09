package prieto.fernando.presentation

import io.reactivex.Single
import prieto.fernando.presentation.model.CompanyInfoUiModel

interface GetCompanyInfo {
    fun execute(): Single<CompanyInfoUiModel>
}
