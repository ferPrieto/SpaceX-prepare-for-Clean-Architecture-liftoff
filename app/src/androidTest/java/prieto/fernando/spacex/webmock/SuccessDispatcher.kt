package prieto.fernando.spacex.webmock

import android.content.Context
import android.net.Uri
import androidx.test.platform.app.InstrumentationRegistry
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import prieto.fernando.spacex.webmock.AssetReaderUtil.asset

const val COMPANY_INFO = "/info"
const val LAUNCHES = "/launches"
const val COMPANY_INFO_SUCCESS = "company_info.json"
const val LAUNCHES_SUCCESS = "launches_success.json"

class SuccessDispatcher(
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
) : Dispatcher() {
    private val responseFilesByPath: Map<String, String> = mapOf(
        COMPANY_INFO to COMPANY_INFO_SUCCESS,
        LAUNCHES to LAUNCHES_SUCCESS
    )

    override fun dispatch(request: RecordedRequest): MockResponse {
        val errorResponse = MockResponse().setResponseCode(404)

        val pathWithoutQueryParams = Uri.parse(request.path).path ?: return errorResponse
        val responseFile = responseFilesByPath[pathWithoutQueryParams]

        return if (responseFile != null) {
            val responseBody = asset(context, responseFile)
            MockResponse().setResponseCode(200).setBody(responseBody)
        } else {
            errorResponse
        }
    }
}