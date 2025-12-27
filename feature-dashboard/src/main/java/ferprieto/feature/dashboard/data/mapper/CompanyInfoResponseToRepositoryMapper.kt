package ferprieto.feature.dashboard.data.mapper

import ferprieto.core.network.model.CompanyInfoResponse
import ferprieto.feature.dashboard.data.model.CompanyInfoRepositoryModel
import javax.inject.Inject

interface CompanyInfoResponseToRepositoryMapper {
    fun toRepositoryModel(response: CompanyInfoResponse): CompanyInfoRepositoryModel
}

class CompanyInfoResponseToRepositoryMapperImpl @Inject constructor() :
    CompanyInfoResponseToRepositoryMapper {
    override fun toRepositoryModel(response: CompanyInfoResponse) =
        CompanyInfoRepositoryModel(
            name = response.name,
            founder = response.founder,
            founded = response.founded.toString(),
            employees = response.employees.toString(),
            launchSites = response.launchSites,
            valuation = response.valuation
        )
}

