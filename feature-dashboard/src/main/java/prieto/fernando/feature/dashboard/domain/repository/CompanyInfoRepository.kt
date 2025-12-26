package prieto.fernando.feature.dashboard.domain.repository

import kotlinx.coroutines.flow.Flow
import prieto.fernando.feature.dashboard.domain.model.CompanyInfoDomainModel

/**
 * Repository interface for company information.
 * This is owned by the Dashboard feature and defines what data it needs.
 * The implementation lives in the core-data module.
 */
interface CompanyInfoRepository {
    suspend fun getCompanyInfo(): Flow<CompanyInfoDomainModel>
}

