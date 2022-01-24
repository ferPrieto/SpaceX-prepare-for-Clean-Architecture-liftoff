package prieto.fernando.data_api.util

import java.net.UnknownHostException

suspend fun <T, F> safeCall(
    call: suspend () -> T,
    mapper: (T) -> F?
): F? = try {
    val response = call()
    val mappedObject = mapper(response)
    mappedObject
} catch (e: UnknownHostException) {
    throw e
}
