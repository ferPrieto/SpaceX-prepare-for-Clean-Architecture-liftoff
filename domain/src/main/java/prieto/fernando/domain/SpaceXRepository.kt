package prieto.fernando.domain

import io.reactivex.Single
import prieto.fernando.domain.model.CompanyInfoDomainModel
import prieto.fernando.domain.model.LaunchDomainModel

interface SpaceXRepository {
    fun getCompanyInfo(): Single<CompanyInfoDomainModel>
    fun getAllLaunches(): Single<List<LaunchDomainModel>>
}