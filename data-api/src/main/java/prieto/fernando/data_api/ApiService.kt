package prieto.fernando.data_api

import io.reactivex.Single
import prieto.fernando.data_api.model.CompanyInfoResponse
import prieto.fernando.data_api.model.LaunchesResponse
import retrofit2.http.GET

interface ApiService {
    @GET("info")
    fun getCompanyInfo(): Single<CompanyInfoResponse>

    @GET("launches")
    fun getAllLaunches(): Single<LaunchesResponse>
}
