package prieto.fernando.core.network

import prieto.fernando.core.network.model.CompanyInfoResponse
import prieto.fernando.core.network.model.LaunchesResponse
import retrofit2.http.GET

interface ApiService {
    @GET("info")
    suspend fun getCompanyInfo(): CompanyInfoResponse

    @GET("launches")
    suspend fun getAllLaunches(): List<LaunchesResponse>
}
