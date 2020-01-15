package prieto.fernando.data_api

import kotlinx.coroutines.flow.Flow
import prieto.fernando.data_api.model.CompanyInfoResponse
import prieto.fernando.data_api.model.LaunchesResponse
import retrofit2.http.GET

interface ApiService {
    @GET("info")
    suspend fun getCompanyInfo(): CompanyInfoResponse

    @GET("launches")
    suspend fun getAllLaunches(): List<LaunchesResponse>
}
