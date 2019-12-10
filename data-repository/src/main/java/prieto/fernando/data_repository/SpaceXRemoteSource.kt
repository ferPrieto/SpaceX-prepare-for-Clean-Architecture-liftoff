package prieto.fernando.data_repository

import io.reactivex.Single
import prieto.fernando.data_repository.model.CompanyInfoRepositoryModel
import prieto.fernando.data_repository.model.LaunchRepositoryModel

interface SpaceXRemoteSource {
    fun getCompanyInfo(): Single<CompanyInfoRepositoryModel>
    fun getAllLaunches(): Single<List<LaunchRepositoryModel>>
}