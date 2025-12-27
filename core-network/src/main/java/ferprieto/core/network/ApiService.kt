package ferprieto.core.network

import ferprieto.core.network.model.CompanyInfoResponse
import ferprieto.core.network.model.LaunchesResponse
import retrofit2.http.GET

interface ApiService {
    @GET("info")
    suspend fun getCompanyInfo(): CompanyInfoResponse

    @GET("launches")
    suspend fun getAllLaunches(): List<LaunchesResponse>
}
