package prieto.fernando.data

import io.reactivex.Single
import prieto.fernando.data.model.CompanyInfoRepositoryModel
import prieto.fernando.data.model.LaunchRepositoryModel

interface SpaceXRemoteSource {
    fun getCompanyInfo(): Single<CompanyInfoRepositoryModel>
    fun getAllLaunches(): Single<List<LaunchRepositoryModel>>
}