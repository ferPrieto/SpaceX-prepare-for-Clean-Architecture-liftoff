package prieto.fernando.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import prieto.fernando.domain.SpaceXRepository
import prieto.fernando.domain.model.CompanyInfoDomainModel
import javax.inject.Inject

interface GetCompanyInfo {
    suspend fun execute(): Result<CompanyInfoDomainModel>
}

class GetCompanyInfoImpl(
    private val spaceXRepository: SpaceXRepository,
    private val ioDispatcher: CoroutineDispatcher
) : GetCompanyInfo {
    @Inject
    constructor(
        spaceXRepository: SpaceXRepository
    ) : this(spaceXRepository, Dispatchers.IO)

    override suspend fun execute(): Result<CompanyInfoDomainModel> =
        withContext(ioDispatcher) {
            try {
                val companyInfo = spaceXRepository.getCompanyInfo()
                Result.success(companyInfo)
            } catch (throwable: Throwable) {
                Result.failure<CompanyInfoDomainModel>(
                    Exception(
                        "Exception retrieving companyInfo",
                        throwable
                    )
                )
            }
        }

}
