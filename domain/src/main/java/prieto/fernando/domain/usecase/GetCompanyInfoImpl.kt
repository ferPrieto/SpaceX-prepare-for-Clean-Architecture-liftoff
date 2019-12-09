package prieto.fernando.domain.usecase

import dagger.Reusable
import javax.inject.Inject

interface GetCompanyInfo{
    fun execute()
}

@Reusable
class GetCompanyInfoImpl @Inject constructor() : GetCompanyInfo{
    override fun execute() {

    }
}